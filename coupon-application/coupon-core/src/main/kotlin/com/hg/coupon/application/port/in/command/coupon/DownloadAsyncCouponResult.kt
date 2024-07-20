package com.hg.coupon.application.port.`in`.command.coupon

import com.hg.coupon.domain.EntityId


data class DownloadAsyncCouponResult(
    val couponPolicyId: EntityId,
    val channelUserId: String,
    val isSuccess: Boolean,
    val msg: String
) {
}
