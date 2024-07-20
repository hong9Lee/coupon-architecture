package com.hg.coupon.domain.coupon.enums

enum class CouponUsageStatus(
    private val value: String
) {
    USABLE("사용 가능한 쿠폰"),
    USED("사용된 쿠폰"),
    EXPIRED("만료된 쿠폰")
}
