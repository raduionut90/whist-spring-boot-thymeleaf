package com.ionutradu.whistspringbootthymeleaf.service;

import com.ionutradu.whistspringbootthymeleaf.model.Card;
import com.ionutradu.whistspringbootthymeleaf.model.Hand;
import com.ionutradu.whistspringbootthymeleaf.model.Player;
import com.ionutradu.whistspringbootthymeleaf.model.Round;
import com.ionutradu.whistspringbootthymeleaf.repository.HandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        Hand hand = handRepository.findById(handId).orElse(null);
        return hand;
    }

    public void sendCard(Player player, String cardId, Hand curentHand) {
        Map<String, String> mapSendCards = curentHand.getCartiJucatori();

        if (mapSendCards.get(player.get_id()) == null) {
            mapSendCards.put(cardId, player.get_id());
            curentHand.setCartiJucatori(mapSendCards);
            handRepository.save(curentHand);
            playerService.removeSendedCard(player, cardId);

        }
    }

    public void save(Hand hand) {
        handRepository.save(hand);
    }

    public List<Card> getSendedCards(Hand curentHand, Player curentPlayer) {
        List<String> sendedCardsId = new ArrayList<>();
        for (Map.Entry<String, String> entry : curentHand.getCartiJucatori().entrySet()) {
            if (curentHand.getCartiJucatori().containsValue(curentPlayer.get_id())){
                String key = entry.getKey();
                sendedCardsId.add(key);
            }
        }
        List<Card> sendedCards = new ArrayList<>();
        for (String id :
                sendedCardsId) {
            Card card = cardService.findById(id);
            sendedCards.add(card);
        }
        return sendedCards;
    }
}
