package co.wadcorp.coupon.application.service.command.fake

import com.hg.coupon.application.port.out.CouponPort
import com.hg.coupon.domain.coupon.Coupon
import com.hg.coupon.domain.coupon.CouponStock
import com.hg.coupon.domain.coupon.enums.CouponUsageStatus
import com.hg.coupon.support.EntityId

open class FakeCouponPort(
) : CouponPort {
    private val couponMap = hashMapOf<EntityId, Coupon>()

    override fun saveCoupon(
        coupon: Coupon,
    ): Coupon {
        couponMap[coupon.couponId] = coupon
        return coupon
    }

    override fun findByCouponPolicyIdAndUserIdAndCouponStatus(
        couponPolicyId: EntityId,
        userId: EntityId,
        couponStatus: CouponUsageStatus,
    ): Coupon? {
        return couponMap.values.find {
            it.couponPolicyId == couponPolicyId && it.userId == userId && it.couponUsageStatus == couponStatus
        }
    }

    override fun findByCouponId(couponId: EntityId): Coupon {
        return couponMap[couponId]
            ?: throw NoSuchElementException("Coupon not found for ID: $couponId")
    }
}
