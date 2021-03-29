package pack.redis.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pack.model.City

@Repository
interface CityRepository : CrudRepository<City?, String?>