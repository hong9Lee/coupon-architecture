package com.hg.coupon.repository

import com.hg.coupon.entity.CouponUserEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface CouponUserRepository: CrudRepository<CouponUserEntity, Long> {
    fun findByChannelAndChannelUserId(
        channel: String,
        channelUserId: String
    ): Optional<CouponUserEntity>
}
