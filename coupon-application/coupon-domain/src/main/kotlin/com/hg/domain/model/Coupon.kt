package com.hg.domain.model

class Coupon (
        val name: String?= null
){
    companion object {
        fun of(name: String): Coupon {
            return Coupon(
                    name = name
            )
        }
    }
}
