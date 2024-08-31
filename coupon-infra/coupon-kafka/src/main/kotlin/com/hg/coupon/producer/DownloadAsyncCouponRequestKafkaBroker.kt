package com.hg.coupon.producer

import co.wadcorp.coupon.message.DownloadAsyncCouponRequestEventMessage
import com.hg.coupon.application.domain.SendDownloadAsyncCouponResult
import com.hg.coupon.application.port.out.message.SendDownloadAsyncCouponBrokerPort
import com.hg.coupon.application.port.out.message.SendDownloadAsyncCouponRequest
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture
private val logger = KotlinLogging.logger {}
@Component
class DownloadAsyncCouponRequestKafkaBroker(
    private val kafkaTemplate: KafkaTemplate<String, DownloadAsyncCouponRequestEventMessage>
) : SendDownloadAsyncCouponBrokerPort {

    @Value("\${spring.kafka.topics.async-download-request}")
    lateinit var asyncRequestTopic: String

    override fun send(request: SendDownloadAsyncCouponRequest): CompletableFuture<SendDownloadAsyncCouponResult> {
        logger.info { "DownloadAsyncCouponRequestKafkaBroker: broker" }
        return kafkaTemplate.send(
            asyncRequestTopic,
            DownloadAsyncCouponRequestEventMessage(
                request.couponPolicyId,
                request.channel,
                request.channelUserId
            )
        )
            .thenApply {
                SendDownloadAsyncCouponResult(
                    couponPolicyId = request.couponPolicyId,
                    channel = request.channel,
                    channelUserId = request.channelUserId,
                    success = true
                )
            }
            .exceptionally {
                SendDownloadAsyncCouponResult(
                    couponPolicyId = request.couponPolicyId,
                    channel = request.channel,
                    channelUserId = request.channelUserId,
                    success = false
                )
            }
    }
}
