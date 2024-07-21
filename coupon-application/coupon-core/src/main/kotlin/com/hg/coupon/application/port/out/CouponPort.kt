package com.hg.coupon.application.port.out

import com.hg.coupon.support.EntityId
import com.hg.coupon.domain.coupon.Coupon
import com.hg.coupon.domain.coupon.CouponStock
import com.hg.coupon.domain.coupon.enums.CouponUsageStatus

interface CouponPort {
    fun findByCouponId(couponId: EntityId): Coupon

    fun saveCoupon(
        coupon: Coupon
    ): Coupon

    fun findByCouponPolicyIdAndUserIdAndCouponStatus(
        couponPolicyId: EntityId,
        userId: EntityId,
        couponStatus: CouponUsageStatus,
    ): Coupon?

}
