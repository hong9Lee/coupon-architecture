package com.hg.coupon.repository

import com.hg.coupon.entity.CouponEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CouponJpaRepository: JpaRepository<CouponEntity, Long> {

}
