package pack.kafka.serializer

import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serializer
import pack.model.City

object CitySerde : Serde<City> {
    override fun serializer(): Serializer<City> = Serializer { topic, data ->
        "name:${data.name},country:${data.country}".toByteArray()
    }

    override fun deserializer(): Deserializer<City> = Deserializer { topic, data ->
        val dataParts = String(data!!).split(",")
        City("1", dataParts[0].split(":")[1], dataParts[1].split(":")[1])
    }


}