
package com.ionutradu.whistspringbootthymeleaf.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document("game")
public class Game {

    @Id
    private String id;

    private int playersNumber;
    private int[] rounds;

    @DBRef(db = "card")
    private List<String> roundsList = new ArrayList<>();

    @DBRef(db = "player")
    private List<Player> playersList = new ArrayList<>();

    @DBRef(db = "card")
    private List<Card> cardsList = new ArrayList<>();

    public Game() {
    }

    public Game(int playersNumber) {
        this.playersNumber = playersNumber;
        this.rounds = setDistribuiri(playersNumber);
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

    public int getPlayersNumber() {
        return playersNumber;
    }

    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
    }

    public int[] getRounds() {
        return rounds;
    }

    public void setRounds(int[] rounds) {
        this.rounds = rounds;
    }

    public List<String> getRoundsList() {
        return roundsList;
    }

    public void setRoundsList(List<String> roundsList) {
        this.roundsList = roundsList;
    }

    public List<Player> getPlayersList() {
        return playersList;
    }

    public void setPlayersList(List<Player> playersList) {
        this.playersList = playersList;
    }

    public List<Card> getCardsList() {
        return cardsList;
    }

    public void setCardsList(List<Card> cardsList) {
        this.cardsList = cardsList;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id='" + id + '\'' +
                ", numarJucatori=" + playersNumber +
                ", distribuiri=" + Arrays.toString(rounds) +
                ", idDistribuiri=" + roundsList +
                ", idJucatori=" + playersList +
                ", idCarti=" + cardsList +
                '}';
    }
}
