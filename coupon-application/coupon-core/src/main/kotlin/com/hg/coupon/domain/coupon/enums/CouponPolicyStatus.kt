package com.hg.coupon.domain.coupon.enums

enum class CouponPolicyStatus(
    private val value: String
) {
    DRAFT("대기"), // 승인 대기
    APPROVE("승인"),
    REJECTION("반려"),

    PUBLISH("발행"),
    TERMINATION("종료"),
    EXPIRED("만료"),

}
