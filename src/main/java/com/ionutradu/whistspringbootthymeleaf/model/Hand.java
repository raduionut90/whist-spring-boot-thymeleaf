
package com.ionutradu.whistspringbootthymeleaf.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedHashMap;
import java.util.Map;

@Document(collection = "hand")
public class Hand {

    @Id
    private String id;

    private Card atu;
    private Card culoare;   //culoarea
    private Map<Player, Card> cartiJucatori = new LinkedHashMap<>();

    public Hand() {
    }

    public Hand(Card atu) {
        this.atu = atu;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Card getAtu() {
        return atu;
    }

    public void setAtu(Card atu) {
        this.atu = atu;
    }

    public Card getCuloare() {
        return culoare;
    }

    public void setCuloare(Card culoare) {
        this.culoare = culoare;
    }

    public Map<Player, Card> getCartiJucatori() {
        return cartiJucatori;
    }

    public void setCartiJucatori(Map<Player, Card> cartiJucatori) {
        this.cartiJucatori = cartiJucatori;
    }
}
