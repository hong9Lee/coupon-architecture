package com.hg.coupon.adapter

import com.hg.coupon.domain.Coupon
import com.hg.coupon.repository.CouponJpaRepository
import org.springframework.stereotype.Component

@Component
class CouponAdapterImpl(private val couponJpaRepository: CouponJpaRepository): CouponAdapter {
    override fun saveCoupon(coupon: Coupon) {
//        val couponEntity = coupon.name?.let { CouponEntity.of(it) }
//        if (couponEntity != null) {
//            couponJpaRepository.save(couponEntity)
//        }

    }


}
