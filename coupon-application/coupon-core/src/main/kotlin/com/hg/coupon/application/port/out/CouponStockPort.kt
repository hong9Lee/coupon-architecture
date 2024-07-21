package com.hg.coupon.application.port.out

import com.hg.coupon.domain.coupon.CouponStock
import com.hg.coupon.support.EntityId


interface CouponStockPort {
    fun findCouponStockByCouponPolicyId(couponPolicyId: EntityId): CouponStock
    fun save(couponStock: CouponStock): CouponStock
}
