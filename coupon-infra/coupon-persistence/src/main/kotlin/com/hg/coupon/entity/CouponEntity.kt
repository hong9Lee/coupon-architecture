package com.hg.coupon.entity

import com.hg.coupon.model.Classfication
import com.hg.coupon.model.EntityId
import jakarta.persistence.*

@Entity
@Table(name = "cp_coupon")
class CouponEntity(

    @Embedded
        @AttributeOverride(name = "value", column = Column(name = "coupon_id"))
        private val couponId: EntityId,

    @Column(name = "coupon_name")
        private val couponName: String? = null,

    @Column(name = "ch_id")
        private val chId: String,

    @Column(name = "user_id")
        private val userId: String,

    @Embedded
        @AttributeOverrides(
                value = [
                    AttributeOverride(name = "majorCategory", column = Column(name = "major_category")),
                    AttributeOverride(name = "middleCategory", column = Column(name = "middle_category")),
                    AttributeOverride(name = "minorCategory", column = Column(name = "minor_category"))

                ]
        )
        private val category: Classfication




) : BaseTimeEntity() {
    companion object {
        fun of(couponId: EntityId, name: String, chId: String, userId: String, classfication: Classfication): CouponEntity {
            return CouponEntity(
                    couponId = couponId,
                    couponName = name,
                    chId = chId,
                    userId = userId,
                    category = classfication
            )
        }
    }


}
