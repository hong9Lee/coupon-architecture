package co.wadcorp.coupon.application.service.command.fake

import com.hg.coupon.application.port.out.CouponPolicyPort
import com.hg.coupon.domain.coupon.CouponPolicy
import com.hg.coupon.exception.ApplicationException
import com.hg.coupon.exception.ErrorCode
import com.hg.coupon.support.Amount
import com.hg.coupon.support.EntityId

class FakeCouponPolicyPort : CouponPolicyPort {
    private val couponPolicyMap = hashMapOf<EntityId, CouponPolicy>()

    override fun findCouponPolicyById(couponPolicyId: EntityId): CouponPolicy {
        return couponPolicyMap[couponPolicyId]
            ?: throw ApplicationException.ofBadRequest(ErrorCode.NOT_FOUND_COUPON_STOCK)
    }

    override fun save(couponPolicy: CouponPolicy): CouponPolicy {
        couponPolicyMap[couponPolicy.couponPolicyId] = couponPolicy
        return couponPolicy
    }

    fun addCouponPolicy(couponPolicy: CouponPolicy) {
        couponPolicyMap[couponPolicy.couponPolicyId] = couponPolicy
    }
}
