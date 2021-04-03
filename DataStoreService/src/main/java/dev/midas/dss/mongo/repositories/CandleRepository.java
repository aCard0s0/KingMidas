package dev.midas.dss.mongo.repositories;

import dev.midas.dss.mongo.domain.Candle;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CandleRepository extends MongoRepository<Candle, String> {

}
