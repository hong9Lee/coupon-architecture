package com.hg.coupon.controller

import com.hg.coupon.controller.data.request.DownloadCouponRequest
import com.hg.coupon.controller.data.response.DownloadCouponResponse
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

    @PostMapping(UrlConstants.쿠폰_다운로드_동기_xLock)
    fun couponDownloadSyncXLock(
        @RequestBody request: DownloadCouponRequest
    ): ResponseEntity<DownloadCouponResponse> {
        return ResponseEntity.ok(
            couponDownloadFacadeService.download(
                request,
                ZonedDateTime.now()
            )
        )
    }
}
