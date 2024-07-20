package com.hg.coupon.repository

import com.hg.coupon.support.EntityId
import com.hg.coupon.entity.CouponEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface CouponRepository : CrudRepository<CouponEntity, Long> {
    fun findByCouponId(couponId: EntityId): Optional<CouponEntity>
}
