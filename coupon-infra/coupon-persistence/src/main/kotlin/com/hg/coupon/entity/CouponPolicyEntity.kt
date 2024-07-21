package com.hg.coupon.entity

import com.hg.coupon.domain.coupon.CouponPolicy
import com.hg.coupon.domain.coupon.enums.CouponPolicyStatus
import com.hg.coupon.support.Amount
import com.hg.coupon.support.EntityId
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.ZonedDateTime

@Entity
@Table(name = "coupon_policy")
class CouponPolicyEntity(

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "coupon_policy_id"))
    private val couponPolicyId: EntityId,

    @Column(name = "coupon_policy_status")
    @Enumerated(EnumType.STRING)
    private val couponPolicyStatus: CouponPolicyStatus,

    @Column(name = "coupon_name")
    private val couponName: String,

    @Column(name = "discount_value")
    private val discountValue: BigDecimal,

    @Column(name = "coupon_start_date_time")
    private val couponStartDateTime: ZonedDateTime,

    @Column(name = "coupon_expire_date_time")
    private val couponExpireDateTime: ZonedDateTime,

) : BaseTimeEntity() {

    fun toDomain(): CouponPolicy {
        return CouponPolicy(
            seq = this.seq,
            couponPolicyId = this.couponPolicyId,
            couponPolicyStatus = this.couponPolicyStatus,
            couponName = this.couponName,
            discountValue = Amount(this.discountValue.toLong()),
            couponStartDateTime = this.couponStartDateTime,
            couponExpireDateTime = this.couponExpireDateTime,
        )
    }

    companion object {
        fun of(couponPolicy: CouponPolicy): CouponPolicyEntity {
            return CouponPolicyEntity(
                couponPolicyId = couponPolicy.couponPolicyId,
                couponPolicyStatus = couponPolicy.couponPolicyStatus,
                couponName = couponPolicy.couponName,
                discountValue = BigDecimal.valueOf(couponPolicy.discountValue.value.toDouble()),
                couponStartDateTime = couponPolicy.couponStartDateTime,
                couponExpireDateTime = couponPolicy.couponExpireDateTime,
            ).apply {
                this.seq = couponPolicy.seq
            }
        }
    }
}
