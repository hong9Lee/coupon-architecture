package com.hg.coupon.producer.config

import co.wadcorp.coupon.message.DownloadAsyncCouponRequestEventMessage
import io.confluent.kafka.serializers.KafkaAvroSerializer
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig
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

    @Value(value = "\${confluent-schema-registry.url}")
    lateinit var confluentSchemaRegistryUrl: String

    @Bean
    fun downloadAsyncCouponRequestEventKafkaTemplate(): KafkaTemplate<String, DownloadAsyncCouponRequestEventMessage> {
        return KafkaTemplate(DefaultKafkaProducerFactory(createConfluentConfigMap()))
    }

    @Bean
    fun genericKafkaTemplate(): KafkaTemplate<String, Any> {
        return KafkaTemplate(DefaultKafkaProducerFactory(createConfluentConfigMap()))
    }

    private fun createConfluentConfigMap(): MutableMap<String, Any> {
        return mutableMapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapAddress,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to KafkaAvroSerializer::class.java.name,
            KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG to confluentSchemaRegistryUrl,
            KafkaAvroSerializerConfig.AUTO_REGISTER_SCHEMAS to true,
            KafkaAvroSerializerConfig.ID_COMPATIBILITY_STRICT to false,
            KafkaAvroSerializerConfig.LATEST_COMPATIBILITY_STRICT to false
        )
    }
}
