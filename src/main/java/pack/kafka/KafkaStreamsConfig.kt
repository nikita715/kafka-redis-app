package pack.kafka

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.Produced
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration
import org.springframework.kafka.config.KafkaStreamsConfiguration
import pack.kafka.serializer.CitySerde
import pack.model.City
import java.util.*


@Configuration
class KafkaStreamsConfig {

    @Value(value = "\${kafka.bootstrapAddress}")
    private lateinit var bootstrapAddress: String

    @Value(value = "\${kafka.sourceTopicName}")
    private lateinit var sourceTopicName: String

    @Value(value = "\${kafka.targetTopicName}")
    private lateinit var targetTopicName: String

    @Bean(name = [KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME])
    fun kStreamsConfigs(): KafkaStreamsConfiguration {
        val props: MutableMap<String, Any> = HashMap()
        props[StreamsConfig.APPLICATION_ID_CONFIG] = "testGroup"
        props[StreamsConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG] = Serdes.String().javaClass.name
        props[StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG] = Serdes.String().javaClass.name
        return KafkaStreamsConfiguration(props)
    }

    @Bean
    fun kStream(kStreamBuilder: StreamsBuilder): KStream<String, City?> {
        val stream = kStreamBuilder
            .stream(sourceTopicName, Consumed.with(Serdes.String(), Serdes.String()))
        val userStream = stream
            .peek { key, value -> println("Received in stream: $key $value") }
            .mapValues { userString: String? -> getUserFromString(userString) }
            .filter { key, value -> value == null }
            .peek { key, value -> println("Processed in stream:$key $value") }

        userStream.to(targetTopicName, Produced.with(Serdes.String(), CitySerde))
        return userStream
    }

    fun getUserFromString(userString: String?): City? =
        if (userString != null) {
            val dataParts = userString.split(",")
            City("1", dataParts[0].split(":")[1], dataParts[1].split(":")[1])
        } else null

}