
package com.ionutradu.whistspringbootthymeleaf.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "card")
public class Card {

    @Id
    private String id;

    private int valoare;
    private int culoare;

    public Card() {
    }

    public Card(int valoare, int culoare) {
        this.valoare = valoare;        
        this.culoare = culoare;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getValoare() {
        return valoare;
    }

    public void setValoare(int valoare) {
        this.valoare = valoare;
    }

    public int getCuloare() {
        return culoare;
    }

    public void setCuloare(int culoare) {
        this.culoare = culoare;
    }

    @Override
    public String toString() {

    String[] culori = {"rosie", "neagra", "romb", "trefla"};

    String[] valori = {"3", "4", "5", "6", "7", "8", "9", "10", "JUVE", "DAMA", "POPA", "AS"};
        //as de rosie
        return valori[valoare] + " de " + culori[culoare];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return valoare == card.valoare &&
                culoare == card.culoare;
    }

    @Override
    public int hashCode() {
        return Objects.hash(valoare, culoare);
    }
}
