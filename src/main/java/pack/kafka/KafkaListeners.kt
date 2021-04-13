package pack.kafka

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.KafkaListener
import pack.model.City
import pack.redis.repository.CityRepository

@Configuration
class KafkaListeners {

    @Autowired
    private lateinit var cityRepository: CityRepository

    @KafkaListener(topics = ["testTopicTarget"], containerFactory = "kafkaListenerContainerFactory")
    fun listenGroupFoo(message: City) {
        cityRepository.save(message)
        println("Received Message in group testGroup: $message")
    }

}