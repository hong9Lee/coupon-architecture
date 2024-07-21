package com.hg.coupon.service

import co.wadcorp.coupon.application.port.`in`.command.DownloadAsyncCouponUseCase
import co.wadcorp.coupon.application.port.`in`.command.coupon.DownloadAsyncCouponResult
import com.hg.coupon.application.port.`in`.command.user.FindUserCommand
import com.hg.coupon.application.port.`in`.command.user.FindUserUseCase
import co.wadcorp.coupon.application.port.`in`.query.FetchDownloadCouponQuery
import co.wadcorp.coupon.application.port.`in`.query.FetchDownloadCouponQueryResult
import co.wadcorp.coupon.application.port.`in`.query.FetchDownloadableCouponQuery
import co.wadcorp.coupon.application.port.`in`.query.FetchDownloadableCouponQueryResult
import com.hg.coupon.validator.CouponValidator
import com.hg.coupon.domain.user.CouponUser
import co.wadcorp.coupon.request.DownloadCouponRequest
import co.wadcorp.coupon.request.DownloadAsyncCouponRequest
import co.wadcorp.coupon.response.DownloadCouponResponse
import com.hg.coupon.application.port.`in`.command.coupon.DownloadCouponCommand
import com.hg.coupon.application.port.`in`.command.coupon.DownloadCouponUseCase
import com.hg.coupon.support.EntityId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime

@Service
class CouponDownloadFacadeService(
    private val couponValidator: CouponValidator,
    private val downloadCouponUseCase: DownloadCouponUseCase,
    private val downloadAsyncCouponUseCase: DownloadAsyncCouponUseCase,
    private val findUserUseCase: FindUserUseCase,
    private val fetchDownloadCouponQuery: FetchDownloadCouponQuery,
    private val fetchDownloadableCouponQuery: FetchDownloadableCouponQuery
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
                ci = downloadCouponRequest.ci
            )
        )

        couponValidator.validateAlreadyDownloaded(couponPolicyId, couponUser.userId)
        val couponPolicy =
            couponValidator.validateCouponPolicy(couponPolicyId, now)
        val couponStock =
            couponValidator.validateCouponStock(couponPolicyId)

        val downloadCouponCommand = DownloadCouponCommand(
            couponPolicyId = couponPolicyId,
            userId = couponUser.userId,
            couponId = EntityId(),
            channel = downloadCouponRequest.channel,
            channelUserId = downloadCouponRequest.channelUserId,
            ci = downloadCouponRequest.ci
        )

        return DownloadCouponResponse.of(
            downloadCouponUseCase.downloadRequest(
                downloadCouponCommand,
                couponPolicy,
                couponStock,
                couponPolicy.maximumDiscountAmount(),
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
                ci = "" // TODO: ci 확인 필요
            )
        )
        couponUser.isExistUser()
        return couponUser
    }
}
