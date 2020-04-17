package com.ionutradu.whistspringbootthymeleaf.service;


import com.ionutradu.whistspringbootthymeleaf.model.Game;
import com.ionutradu.whistspringbootthymeleaf.model.Hand;
import com.ionutradu.whistspringbootthymeleaf.model.Player;
import com.ionutradu.whistspringbootthymeleaf.model.Round;
import com.ionutradu.whistspringbootthymeleaf.repository.PlayerRepository;
import com.ionutradu.whistspringbootthymeleaf.repository.RoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

    @Autowired
    HandService handService;

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

    public Round getRoundByRoundNr(Game game, int roundNr) {
        String roundId = game.getRoundsList().get(roundNr);
        Round round = roundRepository.findById(roundId);
        return round;
    }

    //pentru a vota
    public boolean eRandulMeu(Game game, Round round, Player player) {
        boolean result = false;
        //verific daca nu cumva am votat deja
        if (round.getMapVotate().get(player.get_id()) != null) {
            return false;
        }

        //daca jucatorul este primul poate vota
        if (player.isFirst() == true) {
            return true;
        }
        //verific ce nr are jucatorul in lista game
        int playerNrInList = returnOrder(game, player);

        //aflu id jucator anterior
        String idPlayerAnterior = game.getPlayersList().get(playerNrInList - 1);

        //verific daca jucatorul aterior a votat
        if (round.getMapVotate().containsKey(idPlayerAnterior)) {
            result = true;
        } else {
            result = false;
        }
        return result;

    }

    //indexul jucatorului in game.playerList
    public int returnOrder(Game game, Player player){
        int playerNrInList = 0;
        int gameSize = game.getPlayersList().size();
        for (int i = 0; i < gameSize; i++) {
            if (player.get_id().equals(game.getPlayersList().get(i))){
                playerNrInList = i;
            }
        }
        return playerNrInList;
    }

    public boolean eRandulMeuSaDauCarte(Round round, Hand hand, Player player){
        boolean result = false;
        Game game = gameService.gameByRoundId(round);
        //verific dca au votat toti jucatorii
        if (round.getMapVotate().size() != game.getPlayersNumber()){
            return false;
        }
        //verific daca sunt primul
        if (player.isFirst()){
            return true;
        }
        //verific daca am dat deja carte
        if (hand.getCartiJucatori().containsKey(player.get_id())){
            return false;
        }
        //aflu nr. de ordine in game.playerList si verific daca jucatorul anterior a dat carte
        //verific ce nr are jucatorul in lista game
        int playerNrInList = returnOrder(game, player);

        //aflu id jucator anterior
        String idPlayerAnterior = game.getPlayersList().get(playerNrInList - 1);

        //verific daca jucatorul aterior a votat
        if (hand.getCartiJucatori().containsKey(idPlayerAnterior)) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    public void save(Round round) {
        roundRepository.save(round);
    }

    public Round findById(String roundId) {
        return roundRepository.findById(roundId);
    }

    public void contorizeazaCastigatori(Round round, String idWinner, Hand curentHand) {
        if (curentHand.isTerminat()){
            return;
        }
        if(!round.getMainiCastigate().containsKey(idWinner)){
            round.getMainiCastigate().put(idWinner, 1);
        } else {
            int value = round.getMainiCastigate().get(idWinner);
            round.getMainiCastigate().replace(idWinner, value + 1);
        }
        roundRepository.save(round);
        curentHand.setTerminat(true);
        handService.save(curentHand);
    }

    public void calculeazaPunctaj(Round round) {
        for (String playerId : round.getMapVotate().keySet()) {

            int votate = round.getMapVotate().get(playerId);
            int castigate = round.getMainiCastigate().get(playerId);

            Player player = playerService.findById(playerId);
            int puncte = player.getPuncteCastigate();

            if(votate == castigate){
                player.setPuncteCastigate(puncte + 5 + castigate);
            } else{
                player.setPuncteCastigate(puncte-Math.abs(votate-castigate));
            }
            playerService.save(player);
        }
    }

    public void verificaRoundaCompleta(Game game, Round round) {
        boolean isRundaCompleta = true;
        for (String idPlayer : game.getPlayersList()) {
            Player player = playerService.findById(idPlayer);
            if (player.getCartiCurente() != null){
                isRundaCompleta = false;
            }
        }
        if (isRundaCompleta && !round.isTerminat()) {
            calculeazaPunctaj(round);
            round.setTerminat(true);
            roundRepository.save(round);
        }
    }


}
