package co.wadcorp.coupon.application.service.command.fake

import com.hg.coupon.application.port.out.CouponStockPort
import com.hg.coupon.domain.coupon.CouponStock
import com.hg.coupon.support.EntityId

class FakeCouponStockPort : CouponStockPort {
    private val couponStockMap = hashMapOf<EntityId, CouponStock>()

    override fun findByCouponPolicyIdWithLock(couponPolicyId: EntityId): CouponStock {
        return couponStockMap[couponPolicyId]
            ?: throw NoSuchElementException("Coupon stock not found")
    }

    fun addCouponStock(couponStock: CouponStock) {
        couponStockMap[couponStock.couponPolicyId] = couponStock
    }

    override fun findByCouponPolicyId(couponPolicyId: EntityId): CouponStock {
        TODO("Not yet implemented")
    }

    override fun save(couponStock: CouponStock): CouponStock {
        couponStockMap[couponStock.couponPolicyId] = couponStock
        return couponStock
    }
}
