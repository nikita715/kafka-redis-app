package pack.kafka

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.RangeAssignor
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import pack.kafka.serializer.CityDeserializer
import pack.model.City
import java.util.*

@Configuration
class KafkaConsumerConfig {

    @Value(value = "\${kafka.bootstrapAddress}")
    private lateinit var bootstrapAddress: String

    @Value(value = "\${kafka.groupName}")
    private lateinit var groupName: String

    @Bean
    fun cityConsumerFactory(): ConsumerFactory<String, City> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.GROUP_ID_CONFIG] = groupName + 1
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] =
            CityDeserializer::class.java
        props[ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG] = RangeAssignor::class.java.name
        return DefaultKafkaConsumerFactory(props)
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, City> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, City>()
        factory.consumerFactory = cityConsumerFactory()
        factory.setRecordFilterStrategy {
            println("Checking value: $it")
            it.value() == null
        }
        return factory
    }
}