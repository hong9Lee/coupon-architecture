package com.hg.coupon.application.port.out

import com.hg.coupon.domain.user.CouponUser

interface CouponUserPort {
    fun save(user: CouponUser): CouponUser
    fun getByChannelAndChannelUserId(channel: String, channelUserId: String): CouponUser
}
