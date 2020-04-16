
package com.ionutradu.whistspringbootthymeleaf.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document("player")
public class Player {

    @Id
    private String _id;

    private String nume;
    private int puncteCastigate;
    private boolean first = false;
    private boolean last = false;
    private List<String> cartiCurente = new ArrayList<>();
    private boolean ready;

    public Player(String nume) {
        this.nume = nume;
        this.ready = false;
    }

    @Override
    public String toString() {
        return nume;
  //      return "Jucatorul " + nume + " puncte: " + puncteCastigate + " are cartile: " + cartiCurente.toString();
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getPuncteCastigate() {
        return puncteCastigate;
    }

    public void setPuncteCastigate(int puncteCastigate) {
        this.puncteCastigate = puncteCastigate;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public List<String> getCartiCurente() {
        return cartiCurente;
    }

    public void setCartiCurente(List<String> cartiCurente) {
        this.cartiCurente = cartiCurente;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}
