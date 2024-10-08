package com.hg.coupon.service

import com.hg.coupon.application.port.`in`.command.DownloadAsyncCouponUseCase
import com.hg.coupon.application.port.`in`.command.coupon.DownloadAsyncCouponResult
import com.hg.coupon.application.port.`in`.command.coupon.DownloadCouponCommand
import com.hg.coupon.application.port.`in`.command.coupon.DownloadCouponUseCase
import com.hg.coupon.application.port.`in`.command.user.FindUserCommand
import com.hg.coupon.application.port.`in`.command.user.FindUserUseCase
import com.hg.coupon.controller.data.request.DownloadAsyncCouponRequest
import com.hg.coupon.domain.user.CouponUser
import com.hg.coupon.controller.data.request.DownloadCouponRequest
import com.hg.coupon.controller.data.response.DownloadCouponResponse
import com.hg.coupon.support.EntityId
import com.hg.coupon.validator.CouponValidator
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import java.time.ZonedDateTime
private val logger = KotlinLogging.logger {}
@Service
class CouponDownloadFacadeService(
    private val couponValidator: CouponValidator,
    private val downloadCouponUseCase: DownloadCouponUseCase,
    private val downloadAsyncCouponUseCase: DownloadAsyncCouponUseCase,
    private val findUserUseCase: FindUserUseCase,
) {

    fun downloadSync(
        downloadCouponRequest: DownloadCouponRequest,
        now: ZonedDateTime,
        method: String = "normal"
    ): DownloadCouponResponse {
        val couponPolicyId = EntityId(downloadCouponRequest.couponPolicyId)
        val couponUser = findUserUseCase.findUser(
            FindUserCommand(
                channel = downloadCouponRequest.channel,
                channelUserId = downloadCouponRequest.channelUserId,
            )
        )

        couponValidator.validateAlreadyDownloaded(couponPolicyId, couponUser.userId)
        val couponPolicy =
            couponValidator.validateCouponPolicy(couponPolicyId, now)

        val downloadCouponCommand = DownloadCouponCommand(
            couponPolicyId = couponPolicyId,
            userId = couponUser.userId,
            couponId = EntityId(),
            channel = downloadCouponRequest.channel,
            channelUserId = downloadCouponRequest.channelUserId,
        )

        return DownloadCouponResponse.of(
            downloadCouponUseCase.downloadRequest(
                downloadCouponCommand,
                couponPolicy,
                now,
                method
            )
        )
    }

    fun downloadAsync(
        downloadAsyncCouponRequest: DownloadAsyncCouponRequest
    ): DownloadAsyncCouponResult {
        logger.info { "downloadAsync: 쿠폰_다운로드_비동기_요청" }
        val result = downloadCouponUseCase.checkCouponDownloadable(
            downloadAsyncCouponRequest.to()
        )

        if (result.isSuccess) {
            downloadAsyncCouponUseCase.downloadRequest(downloadAsyncCouponRequest.toSendCommand())
        }
        return result
    }

    private fun getCouponUser(
        channel: String,
        channelUserId: String,
    ): CouponUser {
        val couponUser = findUserUseCase.getUser(
            FindUserCommand(
                channel = channel,
                channelUserId = channelUserId,
            )
        )
        couponUser.isExistUser()
        return couponUser
    }
}
