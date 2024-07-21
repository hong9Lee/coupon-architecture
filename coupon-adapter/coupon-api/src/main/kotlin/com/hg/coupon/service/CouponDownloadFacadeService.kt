package com.hg.coupon.service

import com.hg.coupon.application.port.`in`.command.coupon.DownloadCouponCommand
import com.hg.coupon.application.port.`in`.command.coupon.DownloadCouponUseCase
import com.hg.coupon.application.port.`in`.command.user.FindUserCommand
import com.hg.coupon.application.port.`in`.command.user.FindUserUseCase
import com.hg.coupon.domain.user.CouponUser
import com.hg.coupon.request.DownloadCouponRequest
import com.hg.coupon.response.DownloadCouponResponse
import com.hg.coupon.support.EntityId
import com.hg.coupon.validator.CouponValidator
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class CouponDownloadFacadeService(
    private val couponValidator: CouponValidator,
    private val downloadCouponUseCase: DownloadCouponUseCase,
    private val findUserUseCase: FindUserUseCase,
) {
    fun download(
        downloadCouponRequest: DownloadCouponRequest,
        now: ZonedDateTime
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
                now
            )
        )
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
