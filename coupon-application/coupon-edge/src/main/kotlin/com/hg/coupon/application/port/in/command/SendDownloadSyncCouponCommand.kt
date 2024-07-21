package com.hg.coupon.application.port.`in`.command

data class SendDownloadSyncCouponCommand(
    val couponPolicyId: String,
    val channel: String,
    val channelUserId: String,
) {
}
