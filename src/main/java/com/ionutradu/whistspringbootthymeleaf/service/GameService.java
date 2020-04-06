package com.ionutradu.whistspringbootthymeleaf.service;

import com.ionutradu.whistspringbootthymeleaf.documents.*;
import com.ionutradu.whistspringbootthymeleaf.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void genereazaJucatori(Game game){
        for(int i=0; i<game.getNumarJucatori(); i++){
            Player player = new Player( "Player " + (i+1));
            playerRepository.save(player);
            game.getIdJucatori().add(player.getId());
        }
        String firstPlayerId = game.getIdJucatori().get(0);
        Player firstPlayer = playerRepository.findById(firstPlayerId);
        firstPlayer.setFirst(true);
        playerRepository.save(firstPlayer);

        int lastIdFromIdJucatori = game.getIdJucatori().size()-1;
        String lastPlayerId = game.getIdJucatori().get(lastIdFromIdJucatori);
        Player lastPlayer = playerRepository.findById(lastPlayerId);
        lastPlayer.setLast(true);
        playerRepository.save(lastPlayer);
    }

    public void genereazaCarti(Game game){
        if(game.getNumarJucatori()==3){
            //daca sunt 3 jucatori, genereaza cartile de la 9 la AS
            for(int i=6; i<12; i++){
                for(int j=0; j<4; j++){
                    Card card = new Card(i, j);
                    cardRepository.save(card);
                    game.getIdCarti().add(card.getId());
                }
            }
        }
        if (game.getNumarJucatori()==4){
            for(int i=4; i<12; i++){
                for(int j=0; j<4; j++){
                    Card card = new Card(i, j);
                    cardRepository.save(card);
                    game.getIdCarti().add(card.getId());
                }
            }
        }
        if (game.getNumarJucatori()==5){
            for(int i=2; i<12; i++){
                for(int j=0; j<4; j++){
                    Card card = new Card(i, j);
                    cardRepository.save(card);
                    game.getIdCarti().add(card.getId());
                }
            }
        }
        if (game.getNumarJucatori()==6){
            for(int i=0; i<12; i++){
                for(int j=0; j<4; j++){
                    Card card = new Card(i, j);
                    cardRepository.save(card);
                    game.getIdCarti().add(card.getId());
                }
            }
        }
    }

    public void genereazaRunde(Game game){
        int nrRunde = game.getDistribuiri().length;
        for (int i = 0; i < nrRunde; i++) {
            Round round = new Round(game.getDistribuiri()[i], game.getIdCarti());
            roundRepository.save(round);
            game.getIdDistribuiri().add(round.getId());
        }
    }

    public void genereazaMaini(Game game) {
        for (String roundId : game.getIdDistribuiri()) {
            Round round = roundRepository.findById(roundId);

            for (int i = 0; i < round.getNrMaini(); i++) {
                Hand hand = new Hand(round.getAtuu());
                handRepository.save(hand);
                round.getHandsListId().add(hand.getId());
                roundRepository.save(round);
            }
        }
    }


}
