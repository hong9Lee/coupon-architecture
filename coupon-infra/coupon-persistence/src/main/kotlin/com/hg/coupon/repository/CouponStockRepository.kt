package com.hg.coupon.repository

import com.hg.coupon.entity.CouponStockEntity
import com.hg.coupon.support.EntityId
import jakarta.persistence.LockModeType
import jakarta.persistence.QueryHint
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.QueryHints
import org.springframework.data.repository.CrudRepository
import java.util.*

interface CouponStockRepository : CrudRepository<CouponStockEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(value = [QueryHint(name = "javax.persistence.lock.timeout", value = "5000")])
    fun findByCouponPolicyId(couponPolicyId: EntityId): Optional<CouponStockEntity>
}
