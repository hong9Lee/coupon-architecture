package com.hg.coupon.controller

import com.hg.coupon.application.port.`in`.command.coupon.DownloadAsyncCouponResult
import com.hg.coupon.domain.EntityId
import com.hg.coupon.request.DownloadAsyncCouponRequest
import com.hg.coupon.response.DownloadLimitedCouponResponse
import com.hg.coupon.supprots.UrlConstants
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CouponDownloadController() {

    @PostMapping(UrlConstants.쿠폰_다운로드_동기)
    fun couponDownloadAsync(
        @RequestBody request: DownloadAsyncCouponRequest
    ): ResponseEntity<DownloadLimitedCouponResponse> {
        return ResponseEntity.ok(
            DownloadLimitedCouponResponse.of(
                DownloadAsyncCouponResult(
                    couponPolicyId = EntityId(),
                    channelUserId = "",
                    isSuccess = false,
                    msg = ""
                )
            )
        )
    }
}
