
package com.ionutradu.whistspringbootthymeleaf.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedHashMap;
import java.util.Map;

@Document(collection = "hand")
public class Hand {

    @Id
    private String id;

    private String atu;
    private String culoare;

    // map tip <PlayerID, CardID>
    private Map<String, String> cartiJucatori = new LinkedHashMap<>();

    public Hand(String atu) {
        this.atu = atu;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAtu() {
        return atu;
    }

    public void setAtu(String atu) {
        this.atu = atu;
    }

    public String getCuloare() {
        return culoare;
    }

    public void setCuloare(String culoare) {
        this.culoare = culoare;
    }

    public Map<String, String> getCartiJucatori() {
        return cartiJucatori;
    }

    public void setCartiJucatori(Map<String, String> cartiJucatori) {
        this.cartiJucatori = cartiJucatori;
    }
}
