package com.hg.coupon.controller.data.response

import com.hg.coupon.application.port.`in`.command.coupon.DownloadAsyncCouponResult

data class DownloadLimitedCouponResponse(
    val couponPolicyId: String,
    val channelUserId: String,
    val isSuccess: Boolean,
    val msg: String
) {
    companion object {
        fun of(downloadAsyncCouponResult: DownloadAsyncCouponResult): DownloadLimitedCouponResponse {
            return DownloadLimitedCouponResponse(
                couponPolicyId = downloadAsyncCouponResult.couponPolicyId.value,
                channelUserId = downloadAsyncCouponResult.channelUserId,
                isSuccess = downloadAsyncCouponResult.isSuccess,
                msg = downloadAsyncCouponResult.msg
            )
        }
    }
}
