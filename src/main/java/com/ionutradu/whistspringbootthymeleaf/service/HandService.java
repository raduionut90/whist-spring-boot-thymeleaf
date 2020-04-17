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


    public Hand getCurentHand(Round round, int curentHand) {
        String handId = round.getHandsList().get(curentHand);
        return handRepository.findById(handId).orElse(null);
    }

    public void sendCard(Player player, String cardId, Hand curentHand) {
        Map<String, String> mapSendCards = curentHand.getCartiJucatori();

        if (mapSendCards.get(player.get_id()) == null) {
            mapSendCards.put(player.get_id(), cardId);
            curentHand.setCartiJucatori(mapSendCards);
            handRepository.save(curentHand);
            playerService.removeSendedCard(player, cardId);

        }
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
            idWinner = castigatorByCard(hand, castigatoareAtu);

        } else if (idWinner == null){
            Card primaCarte = cardService.findById(hand.getCuloare());
            Card castigatoarePrima = cardList.stream()
                    .filter(carte -> carte.getCuloare() == primaCarte.getCuloare())
                    .max(Comparator.comparingInt(Card::getValoare)).orElse(null);
            idWinner = castigatorByCard(hand, castigatoarePrima);
        }
        return idWinner;
    }

    private String castigatorByCard(Hand hand, Card winnerCard) {
        String idCastigator = hand.getCartiJucatori().entrySet().stream()
                .filter(entry -> winnerCard.getId().equals(entry.getValue()))
                .map(Map.Entry::getKey).findAny().orElse(null);
        return idCastigator;
    }

    public void verificaManaCompleta(Game game, Hand curentHand, Round round) {
        if (curentHand.getCartiJucatori().size() == game.getPlayersNumber()){
            String idWinner = verificaCastigator(curentHand, round);
            gameService.ordoneazaJucatoriiInFunctieDeCastigator(game, idWinner);
            roundService.contorizeazaCastigatori(round, idWinner, curentHand);
        }
    }
}
