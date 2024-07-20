package com.hg.coupon.application.port.`in`.command.coupon

import com.hg.coupon.support.Amount
import com.hg.coupon.domain.coupon.CouponPolicy
import com.hg.coupon.domain.coupon.CouponStock
import java.time.ZonedDateTime

interface DownloadCouponUseCase {
    fun downloadRequest(
        downloadCouponCommand: DownloadCouponCommand,
        couponPolicy: CouponPolicy,
        couponStock: CouponStock,
        calculatedDiscountExecutionCost: Amount,
        now: ZonedDateTime
    ): DownloadCouponResult

    fun checkCouponDownloadable(
        downloadAsyncCouponCommand: DownloadAsyncCouponCommand
    ): DownloadAsyncCouponResult
}
