package com.hg.coupon.infra.redis.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.hg.coupon.infra.redis.RedisCacheType
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

@Configuration
class RedisConfig(
    private val redisProperties: RedisProperties
) {

    @Bean
    @Primary
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(redisProperties.host, redisProperties.port)
    }

    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, String> {
        val redisTemplate = RedisTemplate<String, String>()
        redisTemplate.connectionFactory = redisConnectionFactory
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = StringRedisSerializer()
        return redisTemplate
    }

    @Bean
    @Primary
    fun redisCacheManager(redisConnectionFactory: RedisConnectionFactory): RedisCacheManager {
        val registerModules = ObjectMapper().registerModules(KotlinModule.Builder().build())
            .registerModule(JavaTimeModule())
            .activateDefaultTyping(
                BasicPolymorphicTypeValidator.builder().allowIfSubType(Any::class.java).build(),
                ObjectMapper.DefaultTyping.EVERYTHING
            )

        val defaultCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer())
            )
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    GenericJackson2JsonRedisSerializer(registerModules)
                )
            )
            .entryTtl(Duration.ofMinutes(redisProperties.ttl))

        val cacheConfigurations = RedisCacheType.values().associate {
            it.cacheName to defaultCacheConfiguration.entryTtl(Duration.ofSeconds(it.expireAfterWrite))
        }

        return RedisCacheManager.builder(redisConnectionFactory)
            .withInitialCacheConfigurations(cacheConfigurations)
            .build()
    }
}
