package co.wadcorp.coupon.application.service.command.fake

import com.hg.coupon.application.port.out.CouponUserPort
import com.hg.coupon.domain.user.CouponUser
import com.hg.coupon.support.EntityId

class FakeCouponUserPort : CouponUserPort {
    private val couponUserMap = hashMapOf<EntityId, CouponUser>()

    override fun save(user: CouponUser): CouponUser {
        couponUserMap[user.userId] = user
        return user
    }

    override fun getByChannelAndChannelUserId(
        channel: String,
        channelUserId: String,
    ): CouponUser {
        return couponUserMap.values.find {
            it.channel == channel && it.channelUserId == channelUserId
        } ?: CouponUser(
            userId = EntityId(),
            channel = channel,
            channelUserId = channelUserId,
            newUser = true
        )
    }

    fun addCouponUser(couponUser: CouponUser) {
        couponUserMap[couponUser.userId] = couponUser
    }
}
