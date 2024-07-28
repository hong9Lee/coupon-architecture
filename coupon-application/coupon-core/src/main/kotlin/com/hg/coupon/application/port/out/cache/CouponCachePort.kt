package com.hg.coupon.application.port.out.cache

interface CouponCachePort {
    fun getCouponStock(key: String): String?
    fun setCouponStock(key: String, value: String)
    fun addToUsersCouponSet(key: String, value: String): Long
    fun removeFromUsersCouponSet(key: String, value: String): Long?
    fun getUsersCouponSet(key: String): Set<String>?
    fun incrementValue(key: String): Long?
    fun decrementValue(key: String): Long
    fun delete(key: String)
}
