package com.hg.coupon.entity

import com.hg.coupon.support.Amount
import com.hg.coupon.support.EntityId
import com.hg.coupon.domain.coupon.Coupon
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.ZonedDateTime

@Entity
@Table(name = "cp_coupon")
class CouponEntity(

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "coupon_id"))
    private val couponId: EntityId,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "coupon_policy_id"))
    private val couponPolicyId: EntityId,

    @Column(name = "coupon_name")
    private val couponName: String,

    @Column(name = "discount_value")
    private val discountValue: BigDecimal,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "user_id"))
    private val userId: EntityId,

    @Column(name = "coupon_start_date_time")
    private val couponStartDateTime: ZonedDateTime,

    @Column(name = "coupon_expire_date_time")
    private val couponExpireDateTime: ZonedDateTime,

    ) : BaseTimeEntity() {
    companion object {
        fun of(coupon: Coupon): CouponEntity {
            return CouponEntity(
                couponId = coupon.couponId,
                couponPolicyId = coupon.couponPolicyId,
                couponName = coupon.couponName,
                discountValue = coupon.discountValue.value,
                userId = coupon.userId,
                couponStartDateTime = coupon.couponStartDateTime,
                couponExpireDateTime = coupon.couponExpireDateTime
            ).apply {
                this.seq = coupon.seq
            }
        }
    }

    fun toDomain(): Coupon {
        return Coupon(
            seq = this.seq,
            couponId = this.couponId,
            couponPolicyId = this.couponPolicyId,
            userId = this.userId,
            couponName = this.couponName,
            discountValue = Amount(this.discountValue.toLong()),
            couponStartDateTime = this.couponStartDateTime,
            couponExpireDateTime = this.couponExpireDateTime
        )
    }
}
