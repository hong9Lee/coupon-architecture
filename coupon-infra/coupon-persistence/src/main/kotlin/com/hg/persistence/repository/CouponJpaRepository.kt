package com.hg.persistence.repository

import com.hg.persistence.entity.CouponEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CouponJpaRepository: JpaRepository<CouponEntity, Long> {

}
