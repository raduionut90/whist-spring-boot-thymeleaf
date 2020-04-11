package com.ionutradu.whistspringbootthymeleaf.service;


import com.ionutradu.whistspringbootthymeleaf.model.Card;
import com.ionutradu.whistspringbootthymeleaf.model.Game;
import com.ionutradu.whistspringbootthymeleaf.model.Player;
import com.ionutradu.whistspringbootthymeleaf.model.Round;
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
        Game game = gameRepository.findGameByRoundsIs(round);
        playerService.clearCartiCurente(game);
        if (round.getNrMaini() == 8){
            Collections.shuffle(round.getColectieCarti());
        }

        for (int i = 0; i < round.getNrMaini(); i++) {
            for (String player : game.getPlayersList()) {
                String nextCard = round.getColectieCarti().get(0);
                playerService.jucatorPreiaCartea(player, nextCard);
                round.getColectieCarti().remove(nextCard);
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
