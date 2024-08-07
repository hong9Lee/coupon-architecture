package co.wadcorp.coupon.application.service.command.fake

import com.hg.coupon.application.port.out.CouponStockPort
import com.hg.coupon.domain.coupon.CouponStock
import com.hg.coupon.support.EntityId

class FakeCouponStockPort : CouponStockPort {
    private val couponStockMap = hashMapOf<EntityId, CouponStock>()

    override fun findCouponStockByCouponPolicyId(couponPolicyId: EntityId): CouponStock {
        return couponStockMap[couponPolicyId]
            ?: throw NoSuchElementException("Coupon stock not found")
    }

    fun addCouponStock(couponStock: CouponStock) {
        couponStockMap[couponStock.couponPolicyId] = couponStock
    }

    override fun save(couponStock: CouponStock): CouponStock {
        couponStockMap[couponStock.couponPolicyId] = couponStock
        return couponStock
    }
}
