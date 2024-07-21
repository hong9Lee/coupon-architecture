package com.hg.coupon.entity

import com.hg.coupon.domain.coupon.CouponStock
import com.hg.coupon.support.EntityId
import jakarta.persistence.*

@Entity
@Table(name = "coupon_stock")
class CouponStockEntity(
    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "coupon_policy_id"))
    private val couponPolicyId: EntityId,

    @Column(name = "stock")
    private val stock: Int,

    @Column(name = "sell_stock")
    private val sellStock: Int

) : BaseTimeEntity() {

    fun toDomain(): CouponStock {
        return CouponStock(
            seq = this.seq,
            couponPolicyId = this.couponPolicyId,
            stock = this.stock,
            sellStock = this.sellStock
        )
    }

    companion object {
        fun of(couponStock: CouponStock): CouponStockEntity {
            return CouponStockEntity(
                couponPolicyId = couponStock.couponPolicyId,
                stock = couponStock.stock,
                sellStock = couponStock.sellStock
            ).apply {
                this.seq = couponStock.seq
            }
        }
    }
}
