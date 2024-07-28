package com.hg.coupon

import com.hg.coupon.infra.redis.config.RedisProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(RedisProperties::class)
class CouponConsumerApplication

fun main(args: Array<String>) {
    runApplication<CouponConsumerApplication>(*args)
}
