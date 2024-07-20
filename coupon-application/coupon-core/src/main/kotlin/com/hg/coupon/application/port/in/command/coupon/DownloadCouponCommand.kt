package com.hg.coupon.application.port.`in`.command.coupon

import com.hg.coupon.domain.coupon.Coupon
import com.hg.coupon.domain.coupon.CouponPolicy
import com.hg.coupon.support.EntityId

data class DownloadCouponCommand(
    val couponPolicyId: EntityId,
    val userId: EntityId,
    val couponId: EntityId,
    val channel: String, // ex CATCHTABLE
    val channelUserId: String, // useSeq
    val ci: String,
) {
    fun toCoupon(couponPolicy: CouponPolicy): Coupon {
        return Coupon(
            couponId = this.couponId,
            couponPolicyId = couponPolicy.couponPolicyId,
            userId = this.userId,
            couponName = couponPolicy.couponName,
            discountValue = couponPolicy.discountValue,
            couponStartDateTime = couponPolicy.couponStartDateTime,
            couponExpireDateTime = couponPolicy.couponExpireDateTime
        )
    }
}
