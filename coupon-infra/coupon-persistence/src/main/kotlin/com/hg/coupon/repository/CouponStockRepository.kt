package com.hg.coupon.repository

import com.hg.coupon.entity.CouponStockEntity
import com.hg.coupon.support.EntityId
import jakarta.persistence.LockModeType
import jakarta.persistence.QueryHint
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.QueryHints
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface CouponStockRepository : CrudRepository<CouponStockEntity, Long> {
    fun findByCouponPolicyId(couponPolicyId: EntityId): Optional<CouponStockEntity>
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(value = [QueryHint(name = "jakarta.persistence.lock.timeout", value = "5000")])
    @Query("SELECT c FROM CouponStockEntity c WHERE c.couponPolicyId = :couponPolicyId")
    fun findByCouponPolicyIdWithLock(@Param("couponPolicyId") couponPolicyId: EntityId): Optional<CouponStockEntity>

}
