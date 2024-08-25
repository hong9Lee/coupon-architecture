package com.hg.coupon.adapter

import com.hg.coupon.application.port.out.CouponStockPort
import com.hg.coupon.domain.coupon.CouponStock
import com.hg.coupon.entity.CouponStockEntity
import com.hg.coupon.exception.ApplicationException
import com.hg.coupon.exception.ErrorCode
import com.hg.coupon.repository.CouponStockRepository
import com.hg.coupon.support.EntityId
import org.springframework.stereotype.Component

@Component
class CouponStockAdapter(
    private val couponStockRepository: CouponStockRepository
) : CouponStockPort {

    override fun findByCouponPolicyId(couponPolicyId: EntityId): CouponStock {
        return couponStockRepository.findByCouponPolicyId(couponPolicyId)
            .map { it.toDomain() }
            .orElseThrow { ApplicationException.ofBadRequest(ErrorCode.NOT_FOUND_COUPON_STOCK) }
    }

    override fun findByCouponPolicyIdWithLock(couponPolicyId: EntityId): CouponStock {
        return couponStockRepository.findByCouponPolicyIdWithLock(couponPolicyId)
            .map { it.toDomain() }
            .orElseThrow { ApplicationException.ofBadRequest(ErrorCode.NOT_FOUND_COUPON_STOCK) }
    }


    override fun save(couponStock: CouponStock): CouponStock {
        val couponStockEntity = CouponStockEntity.of(couponStock)
        val savedCouponStockEntity = couponStockRepository.save(couponStockEntity)
        return savedCouponStockEntity.toDomain()
    }
}
