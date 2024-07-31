package com.hg.coupon.consumer.config

import co.wadcorp.coupon.message.DownloadAsyncCouponRequestEventMessage
import com.hg.coupon.exception.*
import io.confluent.kafka.serializers.KafkaAvroDeserializer
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig
import jakarta.annotation.PostConstruct
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer

@EnableKafka
@Configuration
class CouponKafkaConsumerConfig {

    @Value("\${spring.kafka.consumer.bootstrap-servers}")
    lateinit var bootstrapAddress: String

    @Value(value = "\${confluent-schema-registry.url}")
    lateinit var confluentSchemaRegistryUrl: String

    @Value(value = "\${spring.kafka.consumer.group-id}")
    lateinit var groupId: String

    @PostConstruct
    fun init() {
        println("Bootstrap Address: $bootstrapAddress")
        println("Schema Registry URL: $confluentSchemaRegistryUrl")
        println("Group ID: $groupId")
    }
    @Bean
    fun downloadAsyncRequestEventListenerContainerFactory(
        kafkaTemplate: KafkaTemplate<String, Any>
    ): ConcurrentKafkaListenerContainerFactory<String, DownloadAsyncCouponRequestEventMessage> {

        val factory: ConcurrentKafkaListenerContainerFactory<String, DownloadAsyncCouponRequestEventMessage> =
            ConcurrentKafkaListenerContainerFactory()
        factory.consumerFactory = DefaultKafkaConsumerFactory(createConfluentConsumerProps())

        val recoverer = DeadLetterPublishingRecoverer(kafkaTemplate)
        val backOff = ExponentialBackOffWithMaxRetries(3).apply {
            initialInterval = 2000L // 재시도 간격 2초
            multiplier = 2.0 // 재시도 간격이 2배씩 증가
            maxInterval = 10000L // 최대 재시도 간격 10초
        }

        val errorHandler = DefaultErrorHandler(recoverer, backOff).apply {
            addNotRetryableExceptions(
                OutOfCouponStockException::class.java,
                CouponAlreadyExistException::class.java,
                NotFoundCouponPolicyException::class.java,
                InvalidCouponPolicyStatusException::class.java,
                InvalidCouponPolicyExpireDateTimeException::class.java
            )
        }

        factory.setCommonErrorHandler(errorHandler)
        return factory
    }

    private fun createConfluentConsumerProps(): MutableMap<String, Any> {
        return mutableMapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapAddress,
            ConsumerConfig.GROUP_ID_CONFIG to groupId,
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "latest",
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to ErrorHandlingDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to KafkaAvroDeserializer::class.java.name,
            KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG to confluentSchemaRegistryUrl,
            KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG to true
        )
    }
}
