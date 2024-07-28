package com.hg.coupon.application.port.out.cache

interface CouponCacheTransactionPort {
    fun <T> executeTransaction(action: () -> T): T
}
