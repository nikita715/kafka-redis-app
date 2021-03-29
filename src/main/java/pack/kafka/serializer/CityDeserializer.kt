package pack.kafka.serializer

import org.apache.kafka.common.serialization.Deserializer
import pack.model.City

class CityDeserializer : Deserializer<City> {

    override fun deserialize(topic: String?, data: ByteArray?): City {
        val dataParts = String(data!!).split(",")
        return City("1", dataParts[0].split(":")[1], dataParts[1].split(":")[1])
    }

}