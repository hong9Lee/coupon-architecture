package com.hg.persistence.adapter

import com.hg.core.adapter.CouponAdapter
import com.hg.domain.model.Coupon
import com.hg.persistence.entity.CouponEntity
import com.hg.persistence.repository.CouponJpaRepository
import org.springframework.stereotype.Component

@Component
class CouponAdapterImpl(private val couponJpaRepository: CouponJpaRepository): CouponAdapter {
    override fun saveCoupon(coupon: Coupon) {
        val couponEntity = coupon.name?.let { CouponEntity.of(it) }
        if (couponEntity != null) {
            couponJpaRepository.save(couponEntity)
        }

    }


}
