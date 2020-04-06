package com.ionutradu.whistspringbootthymeleaf.repository;

import com.ionutradu.whistspringbootthymeleaf.documents.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends MongoRepository<Game, String> {

    Game findGameByIdCartiContains(String idCarte);
    Game findGameByIdDistribuiriContains(String idRound);

}
