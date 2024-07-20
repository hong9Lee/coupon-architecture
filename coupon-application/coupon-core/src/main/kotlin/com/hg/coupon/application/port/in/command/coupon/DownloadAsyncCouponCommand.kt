package com.hg.coupon.application.port.`in`.command.coupon

import com.hg.coupon.support.EntityId


data class DownloadAsyncCouponCommand(
    val couponPolicyId: EntityId,
    val channel: String, // ex CATCHTABLE
    val channelUserId: String, // useSeq
) {
    fun getRedisKey(): String {
        return "${this.channel}:${this.channelUserId}"
    }

}
