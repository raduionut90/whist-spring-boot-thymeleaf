package com.ionutradu.whistspringbootthymeleaf.repository;

import com.ionutradu.whistspringbootthymeleaf.model.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends MongoRepository<Player, Integer> {
    Player findBy_id(String id);
    Player findByNume(String nume);
}
