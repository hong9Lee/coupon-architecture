package com.hg.coupon.service

import com.hg.coupon.application.port.`in`.command.coupon.*
import com.hg.coupon.application.port.out.CouponPort
import com.hg.coupon.domain.coupon.CouponPolicy
import com.hg.coupon.domain.coupon.CouponStock
import com.hg.coupon.support.Amount
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
@Transactional
class CouponDownloadService(
    private val couponPort: CouponPort
): DownloadCouponUseCase {
    override fun downloadRequest(
        downloadCouponCommand: DownloadCouponCommand,
        couponPolicy: CouponPolicy,
        couponStock: CouponStock,
        calculatedDiscountExecutionCost: Amount,
        now: ZonedDateTime
    ): DownloadCouponResult {
        TODO("Not yet implemented")
    }

    override fun checkCouponDownloadable(downloadAsyncCouponCommand: DownloadAsyncCouponCommand): DownloadAsyncCouponResult {
        TODO("Not yet implemented")
    }


}
