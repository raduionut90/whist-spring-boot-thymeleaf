
package com.ionutradu.whistspringbootthymeleaf.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document("game")
public class Game {

    @Id
    private String id;

    private int numarJucatori;
    private int[] distribuiri;
    private List<String> idDistribuiri = new ArrayList<>();
    private List<String> idJucatori = new ArrayList<>();
    private List<String> idCarti = new ArrayList<>();

    public Game() {
    }

    public Game(int numarJucatori) {
        this.numarJucatori = numarJucatori;
        this.distribuiri = setDistribuiri(numarJucatori);
    }

    //acestea sunt mainile, reprezentand cartile impartite, in functie de nr de jucatori.
    private int[] setDistribuiri(int numarJuc){
        int[] jocInTrei = {1, 1, 1, 2, 3, 4, 5, 6, 7, 8, 8, 8, 7, 6, 5, 4, 3, 2, 1, 1, 1};
        int[] jocInPatru = {1, 1, 1, 1, 2, 3, 4, 5, 6, 7, 8, 8, 8, 8, 7, 6, 5, 4, 3, 2, 1, 1, 1, 1};
        int[] jocInCinci = {1, 1, 1, 1, 1, 2, 3, 4, 5, 6, 7, 8, 8, 8, 8, 7, 6, 5, 4, 3, 2, 1, 1, 1, 1, 1};
        int[] jocInSase = {1, 1, 1, 1, 1, 1, 2, 3, 4, 5, 6, 7, 8, 8, 8, 8, 7, 6, 5, 4, 3, 2, 1, 1, 1, 1, 1, 1};

        switch(numarJuc){
            case 3:
                return jocInTrei;
            case 4:
                return jocInPatru;
            case 5:
                return jocInCinci;
            case 6:
                return jocInSase;
            default:
                return null;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumarJucatori() {
        return numarJucatori;
    }

    public void setNumarJucatori(int numarJucatori) {
        this.numarJucatori = numarJucatori;
    }

    public int[] getDistribuiri() {
        return distribuiri;
    }

    public void setDistribuiri(int[] distribuiri) {
        this.distribuiri = distribuiri;
    }

    public List<String> getIdDistribuiri() {
        return idDistribuiri;
    }

    public void setIdDistribuiri(List<String> idDistribuiri) {
        this.idDistribuiri = idDistribuiri;
    }

    public List<String> getIdJucatori() {
        return idJucatori;
    }

    public void setIdJucatori(List<String> idJucatori) {
        this.idJucatori = idJucatori;
    }

    public List<String> getIdCarti() {
        return idCarti;
    }

    public void setIdCarti(List<String> idCarti) {
        this.idCarti = idCarti;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id='" + id + '\'' +
                ", numarJucatori=" + numarJucatori +
                ", distribuiri=" + Arrays.toString(distribuiri) +
                ", idDistribuiri=" + idDistribuiri +
                ", idJucatori=" + idJucatori +
                ", idCarti=" + idCarti +
                '}';
    }
}
