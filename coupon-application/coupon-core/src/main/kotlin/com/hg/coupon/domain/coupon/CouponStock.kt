package com.hg.coupon.domain.coupon

import com.hg.coupon.support.EntityId
import com.hg.coupon.exception.OutOfCouponStockException

/**
 * 재고 도메인
 */
class CouponStock(
    val seq: Long? = null,
    val couponPolicyId: EntityId,
    val stock: Int,
    var sellStock: Int
) {

    private fun isRemainStock() = this.stock <= this.sellStock
    fun increaseSellStock(): CouponStock {
        if (isRemainStock()) {
            throw OutOfCouponStockException.of()
        }
        this.sellStock += 1
        return this
    }
}
