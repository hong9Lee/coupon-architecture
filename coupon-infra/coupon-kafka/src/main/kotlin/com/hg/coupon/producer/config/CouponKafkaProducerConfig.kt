package com.hg.coupon.producer.config

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate

@EnableKafka
@Configuration
class CouponKafkaProducerConfig {

    @Value("\${spring.kafka.consumer.bootstrap-servers}")
    lateinit var bootstrapAddress: String

    @Bean
    fun genericKafkaTemplate(): KafkaTemplate<String, Any> {
        return KafkaTemplate(DefaultKafkaProducerFactory(createConfluentConfigMap()))
    }

    private fun createConfluentConfigMap(): MutableMap<String, Any> {
        return mutableMapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapAddress,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        )
    }
}
