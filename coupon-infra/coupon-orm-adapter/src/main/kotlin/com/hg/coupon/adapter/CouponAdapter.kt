package com.hg.coupon.adapter

import com.hg.coupon.application.port.out.CouponPort
import com.hg.coupon.support.EntityId
import com.hg.coupon.domain.coupon.Coupon
import com.hg.coupon.domain.coupon.enums.CouponUsageStatus
import com.hg.coupon.entity.CouponEntity
import com.hg.coupon.exception.ApplicationException
import com.hg.coupon.exception.ErrorCode
import com.hg.coupon.repository.CouponRepository
import org.springframework.stereotype.Component

@Component
class CouponAdapter(
    private val couponRepository: CouponRepository,
) : CouponPort {

    override fun saveCoupon(
        coupon: Coupon
    ): Coupon {
        return couponRepository.save(CouponEntity.of(coupon)).toDomain()
    }

    override fun findByCouponId(couponId: EntityId): Coupon {
        return couponRepository.findByCouponId(couponId)
            .map { it.toDomain() }
            .orElseThrow { ApplicationException.ofBadRequest(ErrorCode.NOT_FOUND_COUPON) }
    }

    override fun findByCouponPolicyIdAndUserIdAndCouponStatus(
        couponPolicyId: EntityId,
        userId: EntityId,
        couponStatus: CouponUsageStatus,
    ): Coupon? {
        return couponRepository.findByCouponPolicyIdAndUserIdAndCouponUsageStatus(
            couponPolicyId,
            userId,
            couponStatus
        )
            .map { it.toDomain() }
            .orElse(null)
    }
}
