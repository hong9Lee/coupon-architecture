package com.hg.coupon.infra.redis

enum class RedisCacheType(
    val cacheName: String,
    val expireAfterWrite: Long,
) {
    COUPON_STOCK("coupon_stock", 12 * 60 * 60), // [couponPolicyId: stock]
    USER_COUPONS("user_coupons", 12 * 60 * 60); // [userId: couponPolicyId]
    fun keyFor(key: String): String {
        return "$cacheName:$key"
    }
}


