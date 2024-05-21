package com.hg.coupon.entity

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

@MappedSuperclass
abstract class BaseTimeEntity (
        @Column(name = "reg_date_time", nullable = false, updatable = false)
        private var regDateTime: ZonedDateTime?= null,

        @Column(name = "mod_date_time", nullable = false)
        private var modDateTime: ZonedDateTime?= null
): BaseEntity(){

    @PrePersist
    fun prePersiste() {
        val now: ZonedDateTime = ZonedDateTime.now().truncatedTo(ChronoUnit.MICROS)
        regDateTime = now
        modDateTime = now
    }

    @PreUpdate
    fun preUpdate() {
        modDateTime = ZonedDateTime.now().truncatedTo(ChronoUnit.MICROS)
    }
}
