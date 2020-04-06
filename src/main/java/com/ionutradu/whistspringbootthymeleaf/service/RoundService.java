package com.ionutradu.whistspringbootthymeleaf.service;


import com.ionutradu.whistspringbootthymeleaf.documents.Game;
import com.ionutradu.whistspringbootthymeleaf.documents.Player;
import com.ionutradu.whistspringbootthymeleaf.documents.Round;
import com.ionutradu.whistspringbootthymeleaf.repository.GameRepository;
import com.ionutradu.whistspringbootthymeleaf.repository.PlayerRepository;
import com.ionutradu.whistspringbootthymeleaf.repository.RoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RoundService {

    @Autowired
    RoundRepository roundRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PlayerService playerService;

    @Autowired
    GameRepository gameRepository;

    public void removeCard(Round round, int nrCard){
        round.getColectieCarti().remove(nrCard);
        roundRepository.save(round);

    }

    public void distribuieCarti(Round round) {
        Game game = gameRepository.findGameByIdDistribuiriContains(round.getId());
        playerService.clearCartiCurente(game);
        if (round.getNrMaini() == 8){
            Collections.shuffle(round.getColectieCarti());
        }

        for (int i = 0; i < round.getNrMaini(); i++) {
            for (String playerID : game.getIdJucatori()) {
                Player player = playerRepository.findById(playerID);

                String nextCardId = round.getColectieCarti().get(0);
                playerService.jucatorPreiaCartea(player, nextCardId);
                round.getColectieCarti().remove(nextCardId);
                roundRepository.save(round);
            }
        }
    }

    public void jucatorCateVotezi(Round round) {
//        for (int i = 0; i < round.getNrMaini(); i++) {
//            String nextCardId = round.getColectieCarti().get(i);
//
//            playerService.jucatorPreiaCartea(nextCardId);
//
//            round.getColectieCarti().remove(i);
//        }


    }
}
