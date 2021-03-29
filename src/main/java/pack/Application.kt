package pack

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

/**
 * The main Spring Boot application class
 */
@SpringBootApplication(scanBasePackages = ["pack"])
@EnableRedisRepositories
class GitplagApplication

/**
 * The application entry point
 */
fun main() {
    runApplication<GitplagApplication>()
}
