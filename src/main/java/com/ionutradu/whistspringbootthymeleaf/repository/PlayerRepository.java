package com.ionutradu.whistspringbootthymeleaf.repository;

import com.ionutradu.whistspringbootthymeleaf.documents.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends MongoRepository<Player, Integer> {
    Player findById(String id);
}
