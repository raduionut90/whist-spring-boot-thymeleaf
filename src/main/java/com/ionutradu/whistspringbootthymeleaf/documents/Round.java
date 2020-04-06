package com.ionutradu.whistspringbootthymeleaf.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;


@Document(collection = "round")
public class Round {
//            nr. distrib. 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30
//            3 jucatori = 1, 1, 1, 2, 3, 4, 5, 6, 7, 8, 8, 8, 7, 6, 5, 4, 3, 2, 1, 1, 1.						        Total 21
//            4 jucatori = 1, 1, 1, 1, 2, 3, 4, 5, 6, 7, 8, 8, 8, 8, 7, 6, 5, 4, 3, 2, 1, 1, 1, 1.					    Total 24
//            5 jucatori = 1, 1, 1, 1, 1, 2, 3, 4, 5, 6, 7, 8, 8, 8, 8, 8, 7, 6, 5, 4, 3, 2, 1, 1, 1, 1, 1			    Total 27
//            6 jucatori = 1, 1, 1, 1, 1, 1, 2, 3, 4, 5, 6, 7, 8, 8, 8, 8, 8, 8, 7, 6, 5, 4, 3, 2, 1, 1, 1, 1, 1, 1		Total 30
//
//    //Fiecare distribuire va avea un nr de maini, de ex. pt 3 juc, distribuirea nr 5 va avea 4 maini.
//    genereazaMaini() // genereaza nr de maini, in functie de nr distribuirii, dupa modelul de mai sus
//    distribuieCarti() // sa imparta fiecarui jucator nr. corespunzator de carti // Hand de 4 imparte 4 carti
//    cateVotate() //intreaba si memoreaza cate maini crede ca face fiecare jucator
//    mainiCastigate //trebuie sa fie un map<nrHand><Jucator> cu lungimea de x Hand, pt fiecare hand in parte, memoreaza jucatorul care a castigat mana respectiva
//    acordaPuncte() //calculeaza punctele pt fiecare jucator, in functie de mainiCastigate

    @Id
    private String id;

    private int nrMaini;
    private List<String> handsListId = new ArrayList<>();
    private Map<Player, Integer> mapVotate = new HashMap<>();
    private int votatePanaAcum;
    private String atuu;
    private List<String> colectieIdCarti;
    private Map<Player, Integer> mainiCastigate = new HashMap<>();

    public Round() {
    }

    public Round(int nrMaini, List<String> colectieCarti) {
        this.nrMaini = nrMaini;
        this.colectieIdCarti = new ArrayList<>(colectieCarti);
        this.atuu = setAtuu();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNrMaini() {
        return nrMaini;
    }

    public void setNrMaini(int nrMaini) {
        this.nrMaini = nrMaini;
    }

    public List<String> getHandsListId() {
        return handsListId;
    }

    public void setHandsListId(List<String> handsListId) {
        this.handsListId = handsListId;
    }

    public Map<Player, Integer> getMapVotate() {
        return mapVotate;
    }

    public void setMapVotate(Map<Player, Integer> mapVotate) {
        this.mapVotate = mapVotate;
    }

    public int getVotatePanaAcum() {
        return votatePanaAcum;
    }

    public void setVotatePanaAcum(int votatePanaAcum) {
        this.votatePanaAcum = votatePanaAcum;
    }

    public String getAtuu() {
        return atuu;
    }

    private String setAtuu(){
        if (nrMaini!=8) {
            Collections.shuffle(colectieIdCarti);
            String atu = colectieIdCarti.get(0);
            colectieIdCarti.remove(0);
            return atu;
        } else {
            return null;
        }
    }

    public List<String> getColectieCarti() {
        return colectieIdCarti;
    }

    public void setColectieCarti(List<String> colectieCarti) {
        this.colectieIdCarti = colectieCarti;
    }

    public Map<Player, Integer> getMainiCastigate() {
        return mainiCastigate;
    }

    public void setMainiCastigate(Map<Player, Integer> mainiCastigate) {
        this.mainiCastigate = mainiCastigate;
    }
}
