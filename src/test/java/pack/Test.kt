package pack

import org.awaitility.kotlin.await
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import pack.model.City
import pack.redis.repository.CityRepository
import java.time.Duration

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest
@ContextConfiguration(classes = [GitplagApplication::class])
class Test {

    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, String>

    @Autowired
    private lateinit var cityRepository: CityRepository

    @Value(value = "\${kafka.topicName}")
    private lateinit var topicName: String

    @Test
    fun testSendMessage() {
        val message = "name:SPb,country:Russia"
        val expectedCity = City("1", "SPb", "Russia")

        kafkaTemplate.send(topicName, message)

        await.pollInterval(Duration.ofSeconds(1)).atMost(Duration.ofSeconds(5)).until {
            cityRepository.findAll().toList()[0]?.equals(expectedCity) ?: false
        }

    }
}