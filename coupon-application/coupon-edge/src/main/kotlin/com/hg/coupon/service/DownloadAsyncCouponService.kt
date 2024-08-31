package com.hg.coupon.service

import com.hg.coupon.application.port.out.message.SendDownloadAsyncCouponBrokerPort
import com.hg.coupon.application.port.`in`.command.DownloadAsyncCouponUseCase
import com.hg.coupon.application.port.`in`.command.SendDownloadAsyncCouponCommand
import com.hg.coupon.application.port.out.cache.CouponCachePort
import com.hg.coupon.application.port.out.cache.CouponCacheTransactionPort
import com.hg.coupon.application.port.out.message.SendDownloadAsyncCouponRequest
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class DownloadAsyncCouponService(
    private val downloadAsyncCouponBrokerPort: SendDownloadAsyncCouponBrokerPort,
    private val couponCachePort: CouponCachePort,
    private val couponCacheTransactionPort: CouponCacheTransactionPort
) : DownloadAsyncCouponUseCase {

    override fun downloadRequest(sendDownloadAsyncCouponCommand: SendDownloadAsyncCouponCommand) {
        val send = downloadAsyncCouponBrokerPort.send(
            SendDownloadAsyncCouponRequest(
                couponPolicyId = sendDownloadAsyncCouponCommand.couponPolicyId,
                channel = sendDownloadAsyncCouponCommand.channel,
                channelUserId = sendDownloadAsyncCouponCommand.channelUserId
            )
        )

        val sendDownloadAsyncCouponResult = send.get()
        // fall back -> redis 쿠폰 재고 원복, 유저 발행 캐시 제거
        if (!sendDownloadAsyncCouponResult.success) {
            logger.error {
                "[kafka topic 발행 실패] couponPolicyId:$sendDownloadAsyncCouponResult.couponPolicyId, " +
                        "channelUserId:$sendDownloadAsyncCouponResult.channelUserId"
            }

            couponCacheTransactionPort.executeTransaction {
                couponCachePort.incrementValue(sendDownloadAsyncCouponResult.couponPolicyId)
                couponCachePort.removeFromUsersCouponSet(
                    sendDownloadAsyncCouponResult.channelUserId,
                    sendDownloadAsyncCouponResult.couponPolicyId
                )
                    ?: logger.error {
                        "재고 제한 쿠폰 유저 발급 기록 삭제 실패 couponPolicyId:$sendDownloadAsyncCouponResult.couponPolicyId, " +
                                "channelUserId:$sendDownloadAsyncCouponResult.channelUserId"
                    }
            }
        }
    }
}
