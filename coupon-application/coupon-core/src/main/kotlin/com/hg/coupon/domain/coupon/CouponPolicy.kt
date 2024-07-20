package com.hg.coupon.domain.coupon

import com.hg.coupon.support.Amount
import com.hg.coupon.support.EntityId
import com.hg.coupon.domain.coupon.enums.CouponPolicyStatus
import java.time.ZonedDateTime

/**
 * 쿠폰 정책 도메인정책
 */
class CouponPolicy(
    val seq: Long? = null,
    val couponPolicyId: EntityId,
    val couponPolicyStatus: CouponPolicyStatus,
    val couponName: String, // 대외 노출용 쿠폰명

    val discountValue: Amount, // 할인 금액

    /** 발행 기간 */
    val couponStartDateTime: ZonedDateTime,
    val couponExpireDateTime: ZonedDateTime,
) {
}
