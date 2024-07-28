package com.hg.coupon.infra.redis

import com.hg.coupon.infra.redis.RedisCacheType.COUPON_STOCK
import com.hg.coupon.infra.redis.RedisCacheType.USER_COUPONS
import com.hg.coupon.application.port.out.cache.CouponCachePort
import com.hg.coupon.exception.ApplicationException
import com.hg.coupon.exception.ErrorCode
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class CouponCacheAdapter(
    private val redisTemplate: RedisTemplate<String, String>
) : CouponCachePort {

    val userCouponCache = USER_COUPONS
    val couponStockCache = COUPON_STOCK

    override fun getCouponStock(key: String): String? {
        return redisTemplate.opsForValue().get(couponStockCache.keyFor(key))
    }

    override fun setCouponStock(key: String, value: String) {
        redisTemplate.opsForValue().set(couponStockCache.keyFor(key), value)
    }

    override fun addToUsersCouponSet(key: String, value: String): Long {
        return redisTemplate.opsForSet().add(userCouponCache.keyFor(key), value)
            ?: throw ApplicationException.ofBadRequest(ErrorCode.DOWNLOAD_COUPON_ERROR)
    }

    override fun removeFromUsersCouponSet(key: String, value: String): Long? {
        return redisTemplate.opsForSet().remove(userCouponCache.keyFor(key), value)
    }

    override fun getUsersCouponSet(key: String): Set<String>? {
        return redisTemplate.opsForSet().members(userCouponCache.keyFor(key))
    }

    override fun incrementValue(key: String): Long? {
        return redisTemplate.opsForValue().increment(couponStockCache.keyFor(key))
    }

    override fun decrementValue(key: String): Long {
        val count = redisTemplate.opsForValue().decrement(couponStockCache.keyFor(key))
        return when {
            count == null -> throw ApplicationException.ofBadRequest(ErrorCode.DOWNLOAD_COUPON_ERROR)
            count < 0 -> {
                incrementValue(key)
                -1L
            }

            else -> count
        }
    }

    override fun delete(key: String) {
        redisTemplate.delete(key)
    }

}
