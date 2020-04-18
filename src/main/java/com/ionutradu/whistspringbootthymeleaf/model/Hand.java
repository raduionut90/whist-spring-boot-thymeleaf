
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
    private int culoare; //primaCarte
    private boolean terminat;
    private String idWinner;

    // map tip <PlayerID, CardID>
    private Map<String, String> cartiJucatori = new LinkedHashMap<>();

    public Hand(String atu) {
        this.atu = atu;
        this.terminat = false;
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

    public int getCuloare() {
        return culoare;
    }

    public void setCuloare(int culoare) {
        this.culoare = culoare;
    }

    public Map<String, String> getCartiJucatori() {
        return cartiJucatori;
    }

    public void setCartiJucatori(Map<String, String> cartiJucatori) {
        this.cartiJucatori = cartiJucatori;
    }

    public boolean isTerminat() {
        return terminat;
    }

    public void setTerminat(boolean terminat) {
        this.terminat = terminat;
    }

    public String getIdWinner() {
        return idWinner;
    }

    public void setIdWinner(String idWinner) {
        this.idWinner = idWinner;
    }
}
