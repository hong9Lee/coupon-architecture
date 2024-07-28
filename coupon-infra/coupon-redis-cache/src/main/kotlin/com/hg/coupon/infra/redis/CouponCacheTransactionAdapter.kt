package com.hg.coupon.infra.redis

import com.hg.coupon.application.port.out.cache.CouponCacheTransactionPort
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class CouponCacheTransactionAdapter(
    private val redisTemplate: RedisTemplate<String, String>
) : CouponCacheTransactionPort {

    override fun <T> executeTransaction(action: () -> T): T {
        return redisTemplate.execute { connection ->
            connection.multi()
            try {
                val result = action()
                connection.exec()
                result
            } catch (e: Exception) {
                connection.discard()
                throw e
            }
        } ?: throw RuntimeException("Failed to execute Redis transaction")
    }
}
