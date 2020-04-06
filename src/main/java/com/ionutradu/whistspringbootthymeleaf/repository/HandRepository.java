package com.ionutradu.whistspringbootthymeleaf.repository;

import com.ionutradu.whistspringbootthymeleaf.documents.Hand;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HandRepository extends MongoRepository<Hand, String> {
}
