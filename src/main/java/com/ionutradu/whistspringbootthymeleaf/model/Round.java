package com.ionutradu.whistspringbootthymeleaf.model;

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
    private List<String> handsList = new ArrayList<>();
    private Map<String, Integer> mapVotate = new HashMap<>();
    private int votatePanaAcum;
    private String atu;
    private List<String> listaCarti;
    //<idPlayer, nrMainiCastigate>
    private Map<String, Integer> mainiCastigate = new HashMap<>();
    private int curentHand;
    private boolean terminat;

    public Round() {
    }

    public Round(int nrMaini, List<String> colectieCarti) {
        this.nrMaini = nrMaini;
        this.listaCarti = new ArrayList<>(colectieCarti);
        this.atu = setAtuu();
        this.curentHand = 0;
        this.terminat = false;
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

    public List<String> getHandsList() {
        return handsList;
    }

    public void setHandsList(List<String> handsList) {
        this.handsList = handsList;
    }

    public Map<String, Integer> getMapVotate() {
        return mapVotate;
    }

    public void setMapVotate(Map<String, Integer> mapVotate) {
        this.mapVotate = mapVotate;
    }

    public int getVotatePanaAcum() {
        return votatePanaAcum;
    }

    public void setVotatePanaAcum(int votatePanaAcum) {
        this.votatePanaAcum = votatePanaAcum;
    }

    public String getAtu() {
        return atu;
    }

    private String setAtuu(){
        if (nrMaini!=8) {
            Collections.shuffle(listaCarti);
            String atu = listaCarti.get(0);
            listaCarti.remove(0);
            return atu;
        } else {
            return null;
        }
    }

    public List<String> getColectieCarti() {
        return listaCarti;
    }

    public void setColectieCarti(List<String> colectieCarti) {
        this.listaCarti = colectieCarti;
    }

    public Map<String, Integer> getMainiCastigate() {
        return mainiCastigate;
    }

    public void setMainiCastigate(Map<String, Integer> mainiCastigate) {
        this.mainiCastigate = mainiCastigate;
    }

    public int getCurentHand() {
        return curentHand;
    }

    public void setCurentHand(int curentHand) {
        this.curentHand = curentHand;
    }

    public boolean isTerminat() {
        return terminat;
    }

    public void setTerminat(boolean terminat) {
        this.terminat = terminat;
    }
}
