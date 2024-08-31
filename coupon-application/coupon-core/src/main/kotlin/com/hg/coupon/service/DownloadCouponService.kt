package com.hg.coupon.service

import com.hg.coupon.application.port.`in`.command.coupon.*
import com.hg.coupon.application.port.out.CouponPort
import com.hg.coupon.application.port.out.CouponStockPort
import com.hg.coupon.application.port.out.cache.CouponCachePort
import com.hg.coupon.application.port.out.cache.CouponCacheTransactionPort
import com.hg.coupon.domain.coupon.CouponPolicy
import com.hg.coupon.exception.ErrorCode
import com.hg.coupon.exception.OutOfCouponStockException
import com.hg.coupon.support.EntityId
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime

private val logger = KotlinLogging.logger {}

@Service
class DownloadCouponService(
    private val couponPort: CouponPort,
    private val couponCachePort: CouponCachePort,
    private val couponCacheTransactionPort: CouponCacheTransactionPort,
    private val couponStockPort: CouponStockPort
) : DownloadCouponUseCase {

    @Transactional(timeout = 10)
    override fun downloadRequest(
        downloadCouponCommand: DownloadCouponCommand,
        couponPolicy: CouponPolicy,
        now: ZonedDateTime,
        method: String
    ): DownloadCouponResult {

        /** 쿠폰 저장 */
        val savedCoupon =
            couponPort.saveCoupon(
                downloadCouponCommand.toCoupon(couponPolicy)
            )

        /**
         * xLock 구간은 [stockEntity 조회 - transaction 종료 혹은 timeout 까지]
         */
        if (method == "xLock") {
            updateStockByLock(couponPolicy)
        } else {
            updateStock(couponPolicy)
        }


        return DownloadCouponResult(
            couponPolicyId = savedCoupon.couponPolicyId,
            couponId = savedCoupon.couponId
        )
    }

    private fun updateStockByLock(couponPolicy: CouponPolicy) {
        val couponStock = couponStockPort.findByCouponPolicyIdWithLock(
            couponPolicyId = couponPolicy.couponPolicyId
        )

        if (couponStock.isRemainStock()) {
            throw OutOfCouponStockException.of()
        }

        couponStock.increaseSellStock()
        couponStockPort.save(couponStock)
    }

    private fun updateStock(couponPolicy: CouponPolicy) {
        val couponStock = couponStockPort.findByCouponPolicyId(
            couponPolicyId = couponPolicy.couponPolicyId
        )

        if (couponStock.isRemainStock()) {
            throw OutOfCouponStockException.of()
        }

        couponStock.increaseSellStock()
        couponStockPort.save(couponStock)
    }

    override fun checkCouponDownloadable(
        downloadAsyncCouponCommand: DownloadAsyncCouponCommand
    ): DownloadAsyncCouponResult {

        logger.info { "checkCouponDownloadable: 쿠폰 다운로드 가능 여부 체크 " }
        return couponCacheTransactionPort.executeTransaction {
            val couponPolicyId = downloadAsyncCouponCommand.couponPolicyId.value
            val channelUserId = downloadAsyncCouponCommand.channelUserId

            val addToUsersCouponSet =
                couponCachePort.addToUsersCouponSet(
                    downloadAsyncCouponCommand.getRedisKey(),
                    couponPolicyId
                )
            logger.info { "checkCouponDownloadable: 유저 쿠폰 다운로드 여부 체크 addToUsersCouponSet:$addToUsersCouponSet " }
            if (addToUsersCouponSet.toInt() != 1) {
                return@executeTransaction DownloadAsyncCouponResult(
                    couponPolicyId = EntityId(couponPolicyId),
                    channelUserId = channelUserId,
                    msg = ErrorCode.ALREADY_ISSUED_COUPON.message,
                    isSuccess = false
                )
            }

            logger.info { "checkCouponDownloadable: 쿠폰 재고 체크 " }
            val couponCount = couponCachePort.decrementValue(couponPolicyId)
            if (couponCount < 0) {
                // 재고가 없으면 사용자 발급 기록 롤백
                couponCachePort.removeFromUsersCouponSet(
                    downloadAsyncCouponCommand.getRedisKey(),
                    couponPolicyId
                )
                    ?: logger.error { "재고 제한 쿠폰 유저 발급 기록 삭제 실패 couponPolicyId:$couponPolicyId, channelUserId:$channelUserId" }


                return@executeTransaction DownloadAsyncCouponResult(
                    couponPolicyId = EntityId(couponPolicyId),
                    channelUserId = channelUserId,
                    msg = ErrorCode.OUT_OF_COUPON_STOCK.message,
                    isSuccess = false
                )
            }


            DownloadAsyncCouponResult(
                couponPolicyId = EntityId(couponPolicyId),
                channelUserId = channelUserId,
                msg = "success",
                isSuccess = true
            )

        }
    }
}
