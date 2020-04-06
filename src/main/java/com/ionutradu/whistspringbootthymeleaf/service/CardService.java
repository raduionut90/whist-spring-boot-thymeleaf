package com.ionutradu.whistspringbootthymeleaf.service;

import com.ionutradu.whistspringbootthymeleaf.documents.Card;
import com.ionutradu.whistspringbootthymeleaf.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;

    public String showCard(String idCard){
        Card card = cardRepository.findById(idCard);
        return card.toString();
    }
}
