package com.hg.coupon.entity

import com.hg.coupon.domain.user.CouponUser
import com.hg.coupon.support.EntityId
import jakarta.persistence.*

@Entity
@Table(name = "coupon_user")
class CouponUserEntity(
    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "user_id"))
    private val userId: EntityId,

    @Column(name = "channel")
    private val channel: String,

    @Column(name = "channel_user_id")
    private val channelUserId: String,

    ) : BaseTimeEntity() {

    fun toDomain(newUser: Boolean = false): CouponUser {
        return CouponUser(
            userId = this.userId,
            channel = this.channel,
            channelUserId = this.channelUserId,
            newUser = newUser
        )
    }

    companion object {
        fun of(couponUser: CouponUser): CouponUserEntity {
            return CouponUserEntity(
                userId = couponUser.userId,
                channel = couponUser.channel,
                channelUserId = couponUser.channelUserId,
            )
        }
    }
}
