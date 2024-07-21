package com.hg.coupon.response

import com.hg.coupon.application.port.`in`.command.coupon.DownloadCouponResult


data class DownloadCouponResponse(
    val couponId: String,
    val couponPolicyId: String
) {
    companion object {
        fun of(downloadCouponResult: DownloadCouponResult): DownloadCouponResponse {
            return DownloadCouponResponse(
                couponId = downloadCouponResult.couponId.value,
                couponPolicyId = downloadCouponResult.couponPolicyId.value
            )
        }
    }
}
