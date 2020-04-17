package com.ionutradu.whistspringbootthymeleaf.service;

import com.ionutradu.whistspringbootthymeleaf.model.Card;
import com.ionutradu.whistspringbootthymeleaf.model.Hand;
import com.ionutradu.whistspringbootthymeleaf.model.Player;
import com.ionutradu.whistspringbootthymeleaf.model.Round;
import com.ionutradu.whistspringbootthymeleaf.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;

    public Card getAtu(Round round){
        String atuId = round.getAtu();
        return cardRepository.findById(atuId);
    }

    public List<Card> getCurentCards(Player curentPlayer) {
        List<Card> cardList = new ArrayList<>();
        for (String cardId :
                curentPlayer.getCartiCurente()) {
            Card curentCard = cardRepository.findById(cardId);
            cardList.add(curentCard);
        }
        return cardList;
    }

    public void save(Card card) {
        cardRepository.save(card);
    }

    public Card findById(String id) {
        return cardRepository.findById(id);
    }

    public List<Card> getListSendedCards(Hand curentHand) {
        Collection<String> cardIdList = curentHand.getCartiJucatori().values();
        List<Card> cardList = new ArrayList<>();
        for (String cardId :
                cardIdList) {
            Card card = cardRepository.findById(cardId);
            cardList.add(card);
        }
        return cardList;
    }
}
