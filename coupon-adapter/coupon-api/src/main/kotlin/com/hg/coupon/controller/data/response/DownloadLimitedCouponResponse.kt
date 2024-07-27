package com.hg.coupon.controller.data.response

import com.hg.coupon.application.port.`in`.command.coupon.DownloadSyncCouponResult

data class DownloadLimitedCouponResponse(
    val couponPolicyId: String,
    val channelUserId: String,
    val isSuccess: Boolean,
    val msg: String
) {
    companion object {
        fun of(downloadSyncCouponResult: DownloadSyncCouponResult): DownloadLimitedCouponResponse {
            return DownloadLimitedCouponResponse(
                couponPolicyId = downloadSyncCouponResult.couponPolicyId.value,
                channelUserId = downloadSyncCouponResult.channelUserId,
                isSuccess = downloadSyncCouponResult.isSuccess,
                msg = downloadSyncCouponResult.msg
            )
        }
    }
}
