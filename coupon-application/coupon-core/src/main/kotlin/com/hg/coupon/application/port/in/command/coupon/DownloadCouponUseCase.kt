package com.hg.coupon.application.port.`in`.command.coupon

import com.hg.coupon.domain.coupon.CouponPolicy
import java.time.ZonedDateTime

interface DownloadCouponUseCase {
    fun downloadRequest(
        downloadCouponCommand: DownloadCouponCommand,
        couponPolicy: CouponPolicy,
        now: ZonedDateTime
    ): DownloadCouponResult

    fun checkCouponDownloadable(
        downloadSyncCouponCommand: DownloadSyncCouponCommand
    ): DownloadSyncCouponResult
}
