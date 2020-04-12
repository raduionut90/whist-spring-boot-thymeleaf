package com.ionutradu.whistspringbootthymeleaf.service;

import com.ionutradu.whistspringbootthymeleaf.model.*;
import com.ionutradu.whistspringbootthymeleaf.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    RoundRepository roundRepository;

    @Autowired
    HandRepository handRepository;

    public void joinPlayer(String gameId, Authentication authentication){
        Player player = new Player(authentication.getName());
        playerRepository.save(player);

        Game game = gameRepository.findById(gameId).orElse(null);
        game.getPlayersList().add(player.get_id());
        gameRepository.save(game);

        checkFirstAndLastPlayer(game, player);
        checkIfAllPlayersJoined(game);
    }

    public void checkFirstAndLastPlayer(Game game, Player player){
        if (game.getPlayersList().size() == 1){
            player.setFirst(true);
            playerRepository.save(player);
        }
        if (game.getPlayersList().size() == game.getPlayersNumber()) {
            player.setLast(true);
            playerRepository.save(player);
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

//    public void genereazaJucatori(Game game){
//        for(int i = 0; i<game.getPlayersNumber(); i++){
//            Player player = new Player( "Player " + (i+1));
//            playerRepository.save(player);
//            game.getPlayersList().add(player.get_id());
//        }
//        Player firstPlayer = game.getPlayersList().get(0);
//        firstPlayer.setFirst(true);
//        playerRepository.save(firstPlayer);
//
//        int lastPlayerId = game.getPlayersList().size() - 1;
//        Player lastPlayer = game.getPlayersList().get(lastPlayerId);
//        lastPlayer.setLast(true);
//        playerRepository.save(lastPlayer);
//    }

    public void genereazaCarti(Game game){
        if(game.getPlayersNumber()==3){
            //daca sunt 3 jucatori, genereaza cartile de la 9 la AS
            for(int i=6; i<12; i++){
                for(int j=0; j<4; j++){
                    Card card = new Card(i, j);
                    cardRepository.save(card);
                    game.getCardsList().add(card.getId());
                }
            }
        }
        if (game.getPlayersNumber()==4){
            for(int i=4; i<12; i++){
                for(int j=0; j<4; j++){
                    Card card = new Card(i, j);
                    cardRepository.save(card);
                    game.getCardsList().add(card.getId());
                }
            }
        }
        if (game.getPlayersNumber()==5){
            for(int i=2; i<12; i++){
                for(int j=0; j<4; j++){
                    Card card = new Card(i, j);
                    cardRepository.save(card);
                    game.getCardsList().add(card.getId());
                }
            }
        }
        if (game.getPlayersNumber()==6){
            for(int i=0; i<12; i++){
                for(int j=0; j<4; j++){
                    Card card = new Card(i, j);
                    cardRepository.save(card);
                    game.getCardsList().add(card.getId());
                }
            }
        }
    }

    public void genereazaRunde(Game game){
        int nrRunde = game.getRounds().length;
        for (int i = 0; i < nrRunde; i++) {
            Round round = new Round(game.getRounds()[i], game.getCardsList());
            roundRepository.save(round);
            game.getRoundsList().add(round.getId());
        }
    }

    public void genereazaMaini(Game game) {
        for (String roundId : game.getRoundsList()) {
            Round round = roundRepository.findById(roundId);

            for (int i = 0; i < round.getNrMaini(); i++) {
                Hand hand = new Hand(round.getAtu());
                handRepository.save(hand);
                round.getHandsList().add(hand.getAtu());
                roundRepository.save(round);
            }
        }
    }

    public Game gameByRoundId(Round round){
        String roundId = round.getId();
        Game game = gameRepository.findGameByRoundsListContains(roundId);
        return game;
    }


}
