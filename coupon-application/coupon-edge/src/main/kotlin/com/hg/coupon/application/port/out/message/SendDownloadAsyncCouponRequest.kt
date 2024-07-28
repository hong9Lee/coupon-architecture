package com.hg.coupon.application.port.out.message

data class SendDownloadAsyncCouponRequest(
    val couponPolicyId: String,
    val channel: String,
    val channelUserId: String,
) {
}
