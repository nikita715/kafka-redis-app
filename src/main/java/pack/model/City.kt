package pack.model

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("City")
data class City(
    @Id
    val id: String,
    val name: String,
    val country: String
)