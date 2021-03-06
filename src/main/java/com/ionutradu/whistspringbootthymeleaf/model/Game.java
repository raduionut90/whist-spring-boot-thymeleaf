
package com.ionutradu.whistspringbootthymeleaf.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document("game")
public class Game {

    @Id
    private String _id;

    private int playersNumber;
    private int[] rounds;

    private List<String> roundsList = new ArrayList<>();

    private List<String> playersList = new ArrayList<>();

    private List<String> cardsList = new ArrayList<>();

    private int curentRound;

    public Game() {
    }

    public Game(int playersNumber) {
        this.playersNumber = playersNumber;
        this.rounds = setDistribuiri(playersNumber);
        this.curentRound = 0;
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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public List<String> getPlayersList() {
        return playersList;
    }

    public void setPlayersList(List<String> playersList) {
        this.playersList = playersList;
    }

    public List<String> getCardsList() {
        return cardsList;
    }

    public void setCardsList(List<String> cardsList) {
        this.cardsList = cardsList;
    }

    public int getCurentRound() {
        return curentRound;
    }

    public void setCurentRound(int curentRound) {
        this.curentRound = curentRound;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id='" + _id + '\'' +
                ", numarJucatori=" + playersNumber +
                ", distribuiri=" + Arrays.toString(rounds) +
                ", idDistribuiri=" + roundsList +
                ", idJucatori=" + playersList +
                ", idCarti=" + cardsList +
                '}';
    }
}
