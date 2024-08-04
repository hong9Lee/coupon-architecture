package com.hg.coupon.application.port.out

import com.hg.coupon.domain.coupon.CouponPolicy
import com.hg.coupon.support.EntityId

interface CouponPolicyPort {
    fun findCouponPolicyById(couponPolicyId: EntityId): CouponPolicy
    fun save(couponPolicy: CouponPolicy): CouponPolicy
}
