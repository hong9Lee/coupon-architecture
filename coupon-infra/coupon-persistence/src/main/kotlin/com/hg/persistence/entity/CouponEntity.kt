package com.hg.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "cp_coupon")
class CouponEntity(
        @Column(name = "coupon_name")
        private val couponName: String? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq", nullable = false, updatable = false)
    open var seq: Long? = null


    companion object {
        fun of(name: String): CouponEntity {
            return CouponEntity(
                    couponName = name
            )
        }
    }


}








