package com.hg.api.controller

import com.hg.core.service.CreateCouponService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CouponController(val couponService: CreateCouponService) {

    @GetMapping("/hello")
    fun createCoupon(): String {
        couponService.saveCoupon()
        return "Hello";
    }


}
