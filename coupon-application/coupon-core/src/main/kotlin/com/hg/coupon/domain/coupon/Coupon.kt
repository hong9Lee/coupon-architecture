package com.hg.coupon.domain.coupon

import com.hg.coupon.domain.coupon.enums.CouponUsageStatus
import com.hg.coupon.support.Amount
import com.hg.coupon.support.EntityId
import java.time.ZonedDateTime

/**
 * 쿠폰 도메인
 * 한번 발급받은 쿠폰의 정책은 변경되지 않고, 상태만 변경된다.
 */
class Coupon(
    val seq: Long? = null,
    val couponId: EntityId,
    val couponPolicyId: EntityId,
    val userId: EntityId,
    val couponName: String,
    val couponUsageStatus: CouponUsageStatus,
    val discountValue: Amount,
    val couponStartDateTime: ZonedDateTime,
    val couponExpireDateTime: ZonedDateTime,
) {
}
