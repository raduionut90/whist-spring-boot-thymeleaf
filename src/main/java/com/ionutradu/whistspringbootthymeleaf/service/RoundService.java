package com.ionutradu.whistspringbootthymeleaf.service;


import com.ionutradu.whistspringbootthymeleaf.model.Game;
import com.ionutradu.whistspringbootthymeleaf.model.Hand;
import com.ionutradu.whistspringbootthymeleaf.model.Player;
import com.ionutradu.whistspringbootthymeleaf.model.Round;
import com.ionutradu.whistspringbootthymeleaf.repository.PlayerRepository;
import com.ionutradu.whistspringbootthymeleaf.repository.RoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public Round getCurentRoundByGame(Game game) {
        String roundId = game.getRoundsList().get(game.getCurentRound());
        Round round = roundRepository.findById(roundId);
        return round;
    }

    //pentru a vota
    public boolean eRandulMeu(Game game, Map map, Player player) {
        boolean result = false;
        //verific daca nu cumva am votat deja
        if (map.get(player.get_id()) != null) {
            return false;
        }

        //daca jucatorul este primul poate vota
        if (player.isFirst() == true) {
            return true;
        }
        //verific ce nr are jucatorul in lista game
        int playerNrInList = returnOrder(game, player);

        //aflu id jucator anterior
        int playerAnterior = (playerNrInList + game.getPlayersNumber() - 1) % game.getPlayersNumber();
        String idPlayerAnterior = game.getPlayersList().get(playerAnterior);

        //verific daca jucatorul aterior a votat
        if (map.containsKey(idPlayerAnterior)) {
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
        handService.save(curentHand);
    }

    public void calculeazaPunctaj(Round round) {
        for (String playerId : round.getMapVotate().keySet()) {

            int votate = round.getMapVotate().get(playerId);
            int castigate = 0;
            if (round.getMainiCastigate().containsKey(playerId)) {
                castigate = round.getMainiCastigate().get(playerId);
            }
            Player player = playerService.findById(playerId);
            int puncte = player.getPuncteCastigate();

            if(votate == castigate){
                player.setPuncteCastigate(puncte + 5 + castigate);
            } else {
                player.setPuncteCastigate(puncte-Math.abs(votate-castigate));
            }
            playerService.save(player);
        }
    }

    public void verificaRoundaCompleta(Game game, Round round) {
        boolean isRundaCompleta = true;
        for (String idPlayer : game.getPlayersList()) {
            Player player = playerService.findById(idPlayer);
            if (player.getCartiCurente().size() != 0){
                return;
            }
        }
        if (isRundaCompleta && !round.isTerminat()) {
            calculeazaPunctaj(round);
            round.setTerminat(true);
            roundRepository.save(round);
            setflags(game);
            gameService.setCurentRound(game);
        }
    }

    private void setflags(Game game) {
        List<Player> playerList = playerService.getAllPlayerFromGame(game);
        int index = game.getCurentRound();

        for (Player player :
                playerList) {
            player.setFirst(false);
            player.setLast(false);
            playerService.save(player);
        }

        Player willBeFirst = playerList.get((index + 1) % playerList.size());
        willBeFirst.setFirst(true);
        playerService.save(willBeFirst);

        Player willBeLast = playerList.get(index % playerList.size());
        willBeLast.setLast(true);
        playerService.save(willBeLast);

    }


    public List<Player> jucatoriCareAuVotat(Round curentRound) {
        List<Player> playerList = new ArrayList<>();
        for (String playerId :
                curentRound.getMapVotate().keySet()) {
            Player player = playerService.findById(playerId);
            playerList.add(player);
        }
        return playerList;
    }

    public List<Integer> catePoateVotaLast(Round round, Player player){
        List<Integer> integerList = new ArrayList<>();
        for (int i = 0; i <= round.getNrMaini(); i++) {
            integerList.add(i);
        }
        int interzis = round.getNrMaini() - round.getVotatePanaAcum();

        Iterator<Integer> it = integerList.iterator();
        while (it.hasNext()) {
            if (it.next() == interzis) {
                it.remove();
            }
        }
        return integerList;
    }

    public void setCurentHand(Round round) {
        int curentHand = round.getCurentHand();
        int nextHand = curentHand + 1;
        if (nextHand < round.getHandsList().size()){
            round.setCurentHand(nextHand);
        }
        roundRepository.save(round);
    }

    public Round getRecentRoundByGame(Game game) {
        if (game.getCurentRound() != 0) {
            String roundId = game.getRoundsList().get(game.getCurentRound() - 1);
            Round round = roundRepository.findById(roundId);
            return round;
        }
        return null;
    }

    public List<Player> jucatoriCareAuDatCarte(Hand curentHand) {
        List<Player> playerList = new ArrayList<>();
        for (String playerId :
                curentHand.getCartiJucatori().keySet()) {
            Player player = playerService.findById(playerId);
            playerList.add(player);
        }
        return playerList;
    }
}
