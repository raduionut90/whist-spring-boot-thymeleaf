package com.ionutradu.whistspringbootthymeleaf.service;

import com.ionutradu.whistspringbootthymeleaf.model.*;
import com.ionutradu.whistspringbootthymeleaf.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    PlayerService playerService;

    @Autowired
    CardService cardService;

    @Autowired
    RoundService roundService;

    @Autowired
    HandService handService;

    public void joinPlayer(String gameId, Authentication authentication){
        Game game = findById(gameId);
        Player player = new Player(authentication.getName());

        if (playerService.getCurentPlayer(authentication) != null){
            return;
        }
        playerService.save(player);

        game.getPlayersList().add(player.get_id());
        gameRepository.save(game);

        checkFirstAndLastPlayer(game, player);
        checkIfAllPlayersJoined(game);
    }

    public void checkFirstAndLastPlayer(Game game, Player player){
        if (game.getPlayersList().size() == 1){
            player.setFirst(true);
            playerService.save(player);
        }
        if (game.getPlayersList().size() == game.getPlayersNumber()) {
            player.setLast(true);
            playerService.save(player);
        }
    }

    public void checkIfAllPlayersJoined(Game game){
        if (game.getPlayersList().size() == game.getPlayersNumber()){
            genereazaCarti(game);
            genereazaRunde(game);
            genereazaMaini(game);
            gameRepository.save(game);
        }
    }

    public void genereazaCarti(Game game){
        if(game.getPlayersNumber()==3){
            //daca sunt 3 jucatori, genereaza cartile de la 9 la AS
            for(int i=6; i<12; i++){
                for(int j=0; j<4; j++){
                    Card card = new Card(i, j);
                    cardService.save(card);
                    game.getCardsList().add(card.getId());
                }
            }
        }
        if (game.getPlayersNumber()==4){
            for(int i=4; i<12; i++){
                for(int j=0; j<4; j++){
                    Card card = new Card(i, j);
                    cardService.save(card);
                    game.getCardsList().add(card.getId());
                }
            }
        }
        if (game.getPlayersNumber()==5){
            for(int i=2; i<12; i++){
                for(int j=0; j<4; j++){
                    Card card = new Card(i, j);
                    cardService.save(card);
                    game.getCardsList().add(card.getId());
                }
            }
        }
        if (game.getPlayersNumber()==6){
            for(int i=0; i<12; i++){
                for(int j=0; j<4; j++){
                    Card card = new Card(i, j);
                    cardService.save(card);
                    game.getCardsList().add(card.getId());
                }
            }
        }
    }

    public void genereazaRunde(Game game){
        int nrRunde = game.getRounds().length;
        for (int i = 0; i < nrRunde; i++) {
            Round round = new Round(game.getRounds()[i], game.getCardsList());
            roundService.save(round);
            game.getRoundsList().add(round.getId());
        }
    }

    public void genereazaMaini(Game game) {
        for (String roundId : game.getRoundsList()) {
            Round round = roundService.findById(roundId);

            for (int i = 0; i < round.getNrMaini(); i++) {
                Hand hand = new Hand(round.getAtu());
                handService.save(hand);
                round.getHandsList().add(hand.getId());
                roundService.save(round);
            }
        }
    }

    public Game gameByRoundId(Round round){
        String roundId = round.getId();
        Game game = gameRepository.findGameByRoundsListContains(roundId);
        return game;
    }


    public Game findById(String gameId) {
        return gameRepository.findById(gameId).orElse(null);
    }

    public void save(Game game) {
        gameRepository.save(game);
    }

    public void setCurentRound(Game game) {
        int curentRound = game.getCurentRound();
        curentRound++;
        if (curentRound < game.getRoundsList().size()){
            game.setCurentRound(curentRound);
            gameRepository.save(game);
        }
    }

    public Game findGameByRound(Round curentRound) {
        return gameRepository.findGameByRoundsListContains(curentRound.getId());
    }
}
