package com.hg.coupon.controller.data.request

import com.hg.coupon.application.port.`in`.command.SendDownloadAsyncCouponCommand
import com.hg.coupon.application.port.`in`.command.coupon.DownloadAsyncCouponCommand
import com.hg.coupon.support.EntityId

data class DownloadAsyncCouponRequest(
    val couponPolicyId: String,
    val channel: String,
    val channelUserId: String,
) {
    fun to(): DownloadAsyncCouponCommand {
        return DownloadAsyncCouponCommand(
            couponPolicyId = EntityId(this.couponPolicyId),
            channel = this.channel,
            channelUserId = this.channelUserId
        )
    }

    fun toSendCommand(): SendDownloadAsyncCouponCommand {
        return SendDownloadAsyncCouponCommand(
            couponPolicyId = this.couponPolicyId,
            channel = this.channel,
            channelUserId = this.channelUserId
        )
    }
}
