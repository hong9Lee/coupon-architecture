package com.hg.coupon.application.port.out

import com.hg.coupon.support.EntityId
import com.hg.coupon.domain.coupon.Coupon
import com.hg.coupon.domain.coupon.CouponStock

interface CouponPort {
    fun findByCouponId(couponId: EntityId): Coupon

    fun saveCoupon(
        coupon: Coupon,
        couponStock: CouponStock
    ): Coupon

}
