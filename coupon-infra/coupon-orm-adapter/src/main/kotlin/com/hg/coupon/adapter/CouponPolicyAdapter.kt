package com.hg.coupon.adapter

import com.hg.coupon.application.port.out.CouponPolicyPort
import com.hg.coupon.domain.coupon.CouponPolicy
import com.hg.coupon.entity.CouponPolicyEntity
import com.hg.coupon.exception.NotFoundCouponPolicyException
import com.hg.coupon.repository.CouponPolicyRepository
import com.hg.coupon.support.Amount
import com.hg.coupon.support.EntityId
import org.springframework.stereotype.Component

@Component
class CouponPolicyAdapter(
    private val couponPolicyRepository: CouponPolicyRepository,
) : CouponPolicyPort {

    override fun findCouponPolicyById(couponPolicyId: EntityId): CouponPolicy {
        return couponPolicyRepository.findByCouponPolicyId(couponPolicyId)
            .map { it.toDomain() }
            .orElseThrow { NotFoundCouponPolicyException.of() }
    }

    override fun save(
        couponPolicy: CouponPolicy
    ): CouponPolicy {
        val couponPolicyEntity = CouponPolicyEntity.of(couponPolicy)
        val savedCouponPolicyEntity = couponPolicyRepository.save(couponPolicyEntity)
        return savedCouponPolicyEntity.toDomain()
    }
}
