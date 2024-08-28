package com.hg.coupon.listener

import co.wadcorp.coupon.message.DownloadAsyncCouponRequestEventMessage
import com.hg.coupon.application.port.`in`.command.coupon.DownloadCouponCommand
import com.hg.coupon.application.port.`in`.command.coupon.DownloadCouponUseCase
import com.hg.coupon.application.port.`in`.command.user.FindUserCommand
import com.hg.coupon.application.port.`in`.command.user.FindUserUseCase
import com.hg.coupon.support.EntityId
import com.hg.coupon.validator.CouponValidator
import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

private val logger = KotlinLogging.logger {}

@Component
class DownloadAsyncCouponRequestEventListener(
    private val findUserUseCase: FindUserUseCase,
    private val downloadCouponUseCase: DownloadCouponUseCase,
    private val couponValidator: CouponValidator,
) {

    @KafkaListener(
        topics = ["\${spring.kafka.topics.async-download-request}"],
        groupId = "\${spring.kafka.consumer.group-id}",
        containerFactory = "downloadAsyncRequestEventListenerContainerFactory",
    )
    fun listen(message: ConsumerRecord<String, DownloadAsyncCouponRequestEventMessage>) {
        val downloadAsyncCouponMessage = message.value()
        logger.info { downloadAsyncCouponMessage }
        val now = ZonedDateTime.now()

        try {
            val couponPolicyId = downloadAsyncCouponMessage.couponPolicyId
            val channel = downloadAsyncCouponMessage.channel
            val channelUserId = downloadAsyncCouponMessage.channelUserId

            val couponUser = findUserUseCase.findUser(
                FindUserCommand(
                    channel = channel,
                    channelUserId = channelUserId,
                )
            )

            couponValidator.validateAlreadyDownloaded(EntityId(couponPolicyId), couponUser.userId)
            val couponPolicy =
                couponValidator.validateCouponPolicy(EntityId(couponPolicyId), now)

            val downloadCouponCommand = DownloadCouponCommand(
                couponPolicyId = EntityId(couponPolicyId),
                userId = couponUser.userId,
                couponId = EntityId(),
                channel = channel,
                channelUserId = channelUserId,
            )

            downloadCouponUseCase.downloadRequest(
                downloadCouponCommand,
                couponPolicy,
                now,
                "xLock"
            )
        } catch (e: Exception) {
            logger.error { "${e}, downloadAsyncCouponMessage:{$downloadAsyncCouponMessage}" }
        }
    }
}
