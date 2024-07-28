package com.hg.coupon.application.domain

data class SendDownloadAsyncCouponResult(
    val couponPolicyId: String,
    val channel: String,
    val channelUserId: String,
    val success: Boolean
) {

}
