package com.ionutradu.whistspringbootthymeleaf.service;

import com.ionutradu.whistspringbootthymeleaf.model.Card;
import com.ionutradu.whistspringbootthymeleaf.model.Player;
import com.ionutradu.whistspringbootthymeleaf.model.Round;
import com.ionutradu.whistspringbootthymeleaf.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;

    public String showCard(String idCard){
        Card card = cardRepository.findById(idCard);
        return card.toString();
    }

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
}
