package com.hg.coupon.repository

import com.hg.coupon.support.EntityId
import org.springframework.data.repository.CrudRepository
import java.util.*

interface CouponPolicyRepository : CrudRepository<CouponPolicyEntity, Long> {
    fun findByCouponPolicyId(couponPolicyId: EntityId): Optional<CouponPolicyEntity>
}
