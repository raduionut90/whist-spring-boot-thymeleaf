package com.ionutradu.whistspringbootthymeleaf.service;

import com.ionutradu.whistspringbootthymeleaf.model.*;
import com.ionutradu.whistspringbootthymeleaf.repository.HandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class HandService {

    @Autowired
    private HandRepository handRepository;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private CardService cardService;

    @Autowired
    private GameService gameService;

    @Autowired
    private RoundService roundService;


    public Hand getCurentHand(Round round) {
        int curentHandNr = round.getCurentHand();
        String handId = round.getHandsList().get(curentHandNr);
        return handRepository.findById(handId).orElse(null);
    }

    public void sendCard(Player player, String cardId, Hand curentHand) {
        int culoare = cardService.findById(cardId).getCuloare();
        //map<idJucator, idCarte>
        Map<String, String> mapSendCards = curentHand.getCartiJucatori();

        //verific daca nu cumva a dat deja carte
        if (mapSendCards.get(player.get_id()) != null) {
            return;
        }
        //verific daca este primul jucator care da carte si stabilesc culoarea
        if (mapSendCards.size() == 0){
            curentHand.setCuloare(culoare);
        }
        mapSendCards.put(player.get_id(), cardId);
        curentHand.setCartiJucatori(mapSendCards);
        handRepository.save(curentHand);
        playerService.removeSendedCard(player, cardId);
    }

    public void save(Hand hand) {
        handRepository.save(hand);
    }

    public Card getSendedCard(Hand curentHand, Player curentPlayer) {
        String cardId = curentHand.getCartiJucatori().get(curentPlayer.get_id());
        return cardService.findById(cardId);
    }

    public String verificaCastigator(Hand hand, Round round) {
        String idWinner = null;
        List<Card> cardList = cardService.getListSendedCards(hand);
        Card atu = cardService.getAtu(round);
        if (hand.getAtu() != null) {
            Card castigatoareAtu = cardList.stream()
                    .filter(carte -> carte.getCuloare() == atu.getCuloare())
                    .max(Comparator.comparingInt(Card::getValoare)).orElse(null);
            if (castigatoareAtu != null) {
                idWinner = castigatorByCard(hand, castigatoareAtu);
            } else {
                Card castigatoarePrima = cardList.stream()
                        .filter(carte -> carte.getCuloare() == hand.getCuloare())
                        .max(Comparator.comparingInt(Card::getValoare)).orElse(null);
                idWinner = castigatorByCard(hand, castigatoarePrima);
            }
        }
        return idWinner;
    }

    private String castigatorByCard(Hand hand, Card winnerCard) {
        String value = winnerCard.getId();
        Map<String, String> mapCarti = hand.getCartiJucatori();
        String id = mapCarti.entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findAny().orElse(null);
        return id;
    }

    public void verificaManaCompleta(Game game, Hand curentHand, Round round) {
        if (curentHand.getCartiJucatori().size() == game.getPlayersNumber()){
            String idWinner = verificaCastigator(curentHand, round);
            setflags(game, idWinner);
            roundService.contorizeazaCastigatori(round, idWinner, curentHand);
            curentHand.setIdWinner(idWinner);
            curentHand.setTerminat(true);
            handRepository.save(curentHand);
            roundService.setCurentHand(round);
        }
    }

    private void setflags(Game game, String idWinner) {
        List<Player> playerList = playerService.getAllPlayerFromGame(game);
        for (int i = 0; i < playerList.size(); i++) {
            playerList.get(i).setFirst(false);
            playerList.get(i).setLast(false);
            if (playerList.get(i).get_id().equals(idWinner)){
                playerList.get(i).setFirst(true);
                if (i - 1 < 0){
                    playerList.get(playerList.size()-1).setLast(true);
                } else {
                    playerList.get(i - 1).setLast(true);
                }
            }
        }

    }

    public Hand getRecentHand(Round curentRound) {
        int recentHandNr = curentRound.getCurentHand() - 1;
        if (recentHandNr >= 0){
            return handRepository.findById(curentRound.getHandsList().get(recentHandNr)).orElse(null);
        } else {
            Game game = gameService.findGameByRound(curentRound);
            if (game.getCurentRound() == 0) {
                return null;
            }
            String recentRoundId = game.getRoundsList().get(game.getCurentRound() - 1);
            Round recentRound = roundService.findById(recentRoundId);
            String recentHandId = recentRound.getHandsList().get(recentRound.getHandsList().size() - 1);
            return handRepository.findById(recentHandId).orElse(null);
        }
    }
}
