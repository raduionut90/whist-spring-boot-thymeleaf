package com.ionutradu.whistspringbootthymeleaf.service;

import com.ionutradu.whistspringbootthymeleaf.model.Card;
import com.ionutradu.whistspringbootthymeleaf.model.Hand;
import com.ionutradu.whistspringbootthymeleaf.model.Player;
import com.ionutradu.whistspringbootthymeleaf.model.Round;
import com.ionutradu.whistspringbootthymeleaf.repository.HandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class HandService {

    @Autowired
    private HandRepository handRepository;

    @Autowired
    private PlayerService playerService;

    @Autowired
    CardService cardService;

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
        String cardId = curentHand.getCartiJucatori().get(curentPlayer);
        return cardService.findById(cardId);
    }
}
