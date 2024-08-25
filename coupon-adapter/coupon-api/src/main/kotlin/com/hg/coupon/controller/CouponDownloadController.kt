package com.hg.coupon.controller

import com.hg.coupon.controller.data.request.DownloadAsyncCouponRequest
import com.hg.coupon.controller.data.request.DownloadCouponRequest
import com.hg.coupon.controller.data.response.DownloadCouponResponse
import com.hg.coupon.controller.data.response.DownloadLimitedCouponResponse
import com.hg.coupon.service.CouponDownloadFacadeService
import com.hg.coupon.supprots.UrlConstants
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.ZonedDateTime

@RestController
class CouponDownloadController(
    private val couponDownloadFacadeService: CouponDownloadFacadeService,
) {

    @PostMapping(UrlConstants.쿠폰_다운로드_동기)
    fun couponDownloadSync(
        @RequestBody request: DownloadCouponRequest
    ): ResponseEntity<DownloadCouponResponse> {
        return ResponseEntity.ok(
            couponDownloadFacadeService.downloadSync(
                request,
                ZonedDateTime.now()
            )
        )
    }

    @PostMapping(UrlConstants.쿠폰_다운로드_동기_xLock)
    fun couponDownloadSyncXLock(
        @RequestBody request: DownloadCouponRequest
    ): ResponseEntity<DownloadCouponResponse> {
        return ResponseEntity.ok(
            couponDownloadFacadeService.downloadSync(
                request,
                ZonedDateTime.now(),
                "xLock"
            )
        )
    }

    @PostMapping(UrlConstants.쿠폰_다운로드_비동기_요청)
    fun couponDownloadAsync(
        @RequestBody request: DownloadAsyncCouponRequest
    ): ResponseEntity<DownloadLimitedCouponResponse> {
        return ResponseEntity.ok(
            DownloadLimitedCouponResponse.of(
                couponDownloadFacadeService.downloadAsync(
                    request
                )
            )
        )
    }
}
