package com.hg.coupon.application.port.`in`.command.coupon

import com.hg.coupon.support.EntityId


data class DownloadCouponResult(
    val couponPolicyId: EntityId,
    val couponId: EntityId
) {
}
