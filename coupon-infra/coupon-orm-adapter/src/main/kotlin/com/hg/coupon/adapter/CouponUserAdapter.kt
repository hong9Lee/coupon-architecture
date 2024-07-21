package com.hg.coupon.adapter

import com.hg.coupon.application.port.out.CouponUserPort
import com.hg.coupon.domain.user.CouponUser
import com.hg.coupon.entity.CouponUserEntity
import com.hg.coupon.repository.CouponUserRepository
import com.hg.coupon.support.EntityId
import org.springframework.stereotype.Component

@Component
class CouponUserAdapter(
    private val couponUserRepository: CouponUserRepository,
) : CouponUserPort {

    override fun save(couponUser: CouponUser): CouponUser {
        val couponUserEntity = CouponUserEntity.of(couponUser)
        val savedCouponUserEntity = couponUserRepository.save(couponUserEntity)
        return savedCouponUserEntity.toDomain()
    }

    override fun getByChannelAndChannelUserId(
        channel: String,
        channelUserId: String,
    ): CouponUser {
        return couponUserRepository.findByChannelAndChannelUserId(channel, channelUserId)
            .map { it.toDomain(newUser = false) }
            .orElseGet {
                CouponUser(
                    userId = EntityId(),
                    channel = channel,
                    channelUserId = channelUserId,
                    newUser = true
                )
            }
    }
}
