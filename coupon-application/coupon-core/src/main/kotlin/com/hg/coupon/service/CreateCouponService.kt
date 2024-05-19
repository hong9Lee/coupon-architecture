package com.hg.coupon.service

import com.hg.coupon.adapter.CouponAdapter
import com.hg.coupon.model.Coupon
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
