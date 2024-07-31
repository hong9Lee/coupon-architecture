package com.hg.coupon.validator

import com.hg.coupon.domain.coupon.CouponPolicy
import com.hg.coupon.support.EntityId
import java.time.ZonedDateTime

interface CouponValidator {
    fun validateAlreadyDownloaded(couponPolicyId: EntityId, couponUserId: EntityId)
    fun validateCouponPolicy(couponPolicyId: EntityId, now: ZonedDateTime): CouponPolicy
}
