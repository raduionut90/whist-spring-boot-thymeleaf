package com.ionutradu.whistspringbootthymeleaf.repository;

import com.ionutradu.whistspringbootthymeleaf.documents.Round;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundRepository extends MongoRepository<Round, Integer> {
    Round findById(String id);

}
