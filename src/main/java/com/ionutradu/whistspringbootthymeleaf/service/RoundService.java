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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class RoundService {

    @Autowired
    RoundRepository roundRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PlayerService playerService;

    @Autowired
    GameService gameService;

    public void removeCard(Round round, int nrCard){
        round.getColectieCarti().remove(nrCard);
        roundRepository.save(round);

    }

    public void distribuieCarti(Round round) {

        //verific daca colectia de carti a rundei este intacta (daca nu inseamna ca au fost deja distribuite carti)
        if (round.getColectieCarti().size() != 23 && round.getAtu() != null ||
                round.getColectieCarti().size() != 24 && round.getAtu() == null ){
            return;
        }
        Game game = gameService.gameByRoundId(round);
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

    public void jucatorCateVotezi(Round round, Player player, int votateDeJucator) {
            Map<String, Integer> mapVotate = round.getMapVotate();
            if (mapVotate.get(player.get_id()) == null) {
                mapVotate.put(player.get_id(), votateDeJucator);
            }
            round.setMapVotate(mapVotate);

            int votate = round.getVotatePanaAcum() + round.getMapVotate().get(player.get_id());
            round.setVotatePanaAcum(votate);
            roundRepository.save(round);
    }

    public List<Round> getRounds(Game game){
        List<Round> roundList = new ArrayList<>();
        for (String roundId :
                game.getRoundsList()) {
            Round round = roundRepository.findById(roundId);
            roundList.add(round);
        }
        return roundList;
    }

    public Round getRound(String roundId) {
        return roundRepository.findById(roundId);
    }

    public Round getRoundByRoundNr(Game game, int roundNr) {
        String roundId = game.getRoundsList().get(roundNr);
        Round round = roundRepository.findById(roundId);
        return round;
    }

    public boolean eRandulMeu(Game game, Round round, Player player){
        boolean result = false;
        //verific daca nu cumva am votat deja
        if (round.getMapVotate().get(player.get_id()) != null){
            return false;
        }
        int playerNrInList = 0;
        //daca jucatorul este primul poate vota
        if (player.isFirst() == true){
            result = true;
            return true;
        } else{
            //verific ce nr are jucatorul in lista game
        int gameSize = game.getPlayersList().size();
            for (int i = 0; i < gameSize; i++) {
                if (player.get_id().equals(game.getPlayersList().get(i))){
                    playerNrInList = i;
                }
            }
        }

        //aflu id jucator anterior
        String idPlayerAnterior = game.getPlayersList().get(playerNrInList-1);

        //verific daca jucatorul aterior a votat
        if (round.getMapVotate().containsKey(idPlayerAnterior)){
            result = true;
        }
        return result;
    }
}
