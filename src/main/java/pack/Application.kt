package pack

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.EnableKafkaStreams

/**
 * The main Spring Boot application class
 */
@SpringBootApplication(scanBasePackages = ["pack"])
@EnableKafka
@EnableKafkaStreams
@EnableRedisRepositories
class GitplagApplication

/**
 * The application entry point
 */
fun main() {
    runApplication<GitplagApplication>()
}
