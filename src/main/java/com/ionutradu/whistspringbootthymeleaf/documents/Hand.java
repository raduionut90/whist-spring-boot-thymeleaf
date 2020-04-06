
package com.ionutradu.whistspringbootthymeleaf.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

@Document(collection = "hand")
public class Hand {

    @Id
    private String id;

    private String atuId;
    private Card culoare;   //culoarea
    private Map<Player, Card> cartiJucatori = new LinkedHashMap<>();

    public Hand() {
    }

    public Hand(String atuId) {
        this.atuId = atuId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAtu() {
        return atuId;
    }

    public void setAtu(String atu) {
        this.atuId = atu;
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
