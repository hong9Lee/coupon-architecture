package com.hg.coupon.request

import com.hg.coupon.application.port.`in`.command.coupon.DownloadCouponCommand
import com.hg.coupon.support.EntityId

class DownloadCouponRequest(
    val couponPolicyId: String,
    val channel: String,
    val channelUserId: String,
) {
    fun toServiceRequest(): DownloadCouponCommand {
        return DownloadCouponCommand(
            couponPolicyId = EntityId(this.couponPolicyId),
            userId = EntityId(),
            couponId = EntityId(),
            channel = this.channel,
            channelUserId = this.channelUserId,
        )
    }

}
