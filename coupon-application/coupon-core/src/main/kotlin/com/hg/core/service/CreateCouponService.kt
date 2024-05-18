package com.hg.core.service

import com.hg.core.adapter.CouponAdapter
import com.hg.domain.model.Coupon
import org.springframework.stereotype.Service

@Service
class CreateCouponService (
        private val couponAdapter: CouponAdapter
){

    fun saveCoupon() {
        val coupon = Coupon.of("test")
        couponAdapter.saveCoupon(coupon)
    }


}
