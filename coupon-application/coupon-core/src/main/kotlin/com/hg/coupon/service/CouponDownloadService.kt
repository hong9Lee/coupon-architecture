package com.hg.coupon.service

import com.hg.coupon.application.port.`in`.command.coupon.*
import com.hg.coupon.application.port.out.CouponPort
import com.hg.coupon.application.port.out.CouponStockPort
import com.hg.coupon.domain.coupon.CouponPolicy
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime

@Service
class CouponDownloadService(
    private val couponPort: CouponPort,
    private val couponStockPort: CouponStockPort
): DownloadCouponUseCase {

    @Transactional(timeout = 10)
    override fun downloadRequest(
        downloadCouponCommand: DownloadCouponCommand,
        couponPolicy: CouponPolicy,
        now: ZonedDateTime
    ): DownloadCouponResult {

        /** 쿠폰 저장 */
        val savedCoupon =
            couponPort.saveCoupon(
                downloadCouponCommand.toCoupon(couponPolicy)
            )

        return DownloadCouponResult(
            couponPolicyId = savedCoupon.couponPolicyId,
            couponId = savedCoupon.couponId
        )
    }

    override fun checkCouponDownloadable(downloadSyncCouponCommand: DownloadSyncCouponCommand): DownloadSyncCouponResult {
        TODO("Not yet implemented")
    }


}
