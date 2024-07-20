package com.hg.coupon.domain.coupon

import com.hg.coupon.domain.coupon.enums.CouponUsageStatus
import com.hg.coupon.domain.coupon.enums.CouponUsageStatus.*
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.math.BigDecimal

/**
 * 쿠폰 사용 도메인 (사용 가능, 사용된, 만료 상태가 존재함.)
 */
data class CouponUsage(
    @Enumerated(EnumType.STRING)
    var couponUsageStatus: CouponUsageStatus = USABLE,
    val transactionId: String = USABLE.name,  // 요청값의 원 주문 아이디
    val discountedAmount: BigDecimal = BigDecimal.ZERO,
) {
}
