package co.wadcorp.coupon.infra.adapter

import co.wadcorp.coupon.application.port.out.CouponStockPort
import co.wadcorp.coupon.domain.coupon.CouponStock
import co.wadcorp.coupon.domain.support.EntityId
import co.wadcorp.coupon.exception.ApplicationException
import co.wadcorp.coupon.exception.ErrorCode
import co.wadcorp.coupon.persistence.entity.CouponStockEntity
import co.wadcorp.coupon.persistence.repository.CouponStockRepository
import org.springframework.stereotype.Component

@Component
class CouponStockAdapter (
    private val couponStockRepository: CouponStockRepository
): CouponStockPort {

    override fun findCouponStockByCouponPolicyId(couponPolicyId: EntityId): CouponStock {
        return couponStockRepository.findByCouponPolicyId(couponPolicyId)
            .map { it.toDomain() }
            .orElseThrow { ApplicationException.ofBadRequest(ErrorCode.NOT_FOUND_COUPON_STOCK) }
    }

    override fun save(couponStock: CouponStock): CouponStock {
        val couponStockEntity = CouponStockEntity.of(couponStock)
        val savedCouponStockEntity = couponStockRepository.save(couponStockEntity)
        return savedCouponStockEntity.toDomain()
    }
}
