package com.hg.coupon.controller

import com.hg.coupon.application.port.`in`.command.coupon.DownloadAsyncCouponResult
import com.hg.coupon.application.port.`in`.command.coupon.DownloadCouponCommand
import com.hg.coupon.application.port.`in`.command.coupon.DownloadCouponUseCase
import com.hg.coupon.support.EntityId
import com.hg.coupon.request.DownloadAsyncCouponRequest
import com.hg.coupon.response.DownloadLimitedCouponResponse
import com.hg.coupon.supprots.UrlConstants
import jakarta.persistence.criteria.CriteriaBuilder.Case
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CouponDownloadController(
    private val downloadCouponUseCase: DownloadCouponUseCase,
) {

    @PostMapping(UrlConstants.쿠폰_다운로드_동기_xLock)
    fun couponDownloadAsync(
        @RequestBody request: DownloadAsyncCouponRequest
    ): ResponseEntity<DownloadLimitedCouponResponse> {



        val downloadCouponCommand = DownloadCouponCommand(
            couponPolicyId = couponPolicyId,
            userId = couponUser.userId,
            couponId = EntityId(),
            channel = downloadCouponRequest.channel,
            channelUserId = downloadCouponRequest.channelUserId,
            ci = downloadCouponRequest.ci
        )

        downloadCouponUseCase.downloadRequest()


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
