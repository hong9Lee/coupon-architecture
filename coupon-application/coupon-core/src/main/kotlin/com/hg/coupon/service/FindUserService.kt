package com.hg.coupon.service

import com.hg.coupon.application.port.`in`.command.user.FindUserCommand
import com.hg.coupon.application.port.`in`.command.user.FindUserUseCase
import com.hg.coupon.application.port.`in`.command.user.UserResult
import com.hg.coupon.application.port.out.CouponUserPort
import com.hg.coupon.domain.user.CouponUser
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class FindUserService(
    private val couponUserPort: CouponUserPort
) : FindUserUseCase {
    override fun findUser(findUserCommand: FindUserCommand): UserResult {
        val findPointUser = couponUserPort.getByChannelAndChannelUserId(
            findUserCommand.channel,
            findUserCommand.channelUserId,
        )

        if (findPointUser.isNewUser()) {
            couponUserPort.save(findPointUser)
        }

        return UserResult(
            userId = findPointUser.userId,
            channel = findPointUser.channel,
            channelUserId = findPointUser.channelUserId,
        )
    }

    override fun getUser(findUserCommand: FindUserCommand): CouponUser {
        return couponUserPort.getByChannelAndChannelUserId(
            findUserCommand.channel,
            findUserCommand.channelUserId
        )
    }
}
