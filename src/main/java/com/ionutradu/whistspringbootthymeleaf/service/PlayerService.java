package com.ionutradu.whistspringbootthymeleaf.service;

import com.ionutradu.whistspringbootthymeleaf.model.Card;
import com.ionutradu.whistspringbootthymeleaf.model.Game;
import com.ionutradu.whistspringbootthymeleaf.model.Player;
import com.ionutradu.whistspringbootthymeleaf.repository.GameRepository;
import com.ionutradu.whistspringbootthymeleaf.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    RoundService roundService;

    public void jucatorPreiaCartea(String playerId, String nextCard) {
        Player player = playerRepository.findBy_id(playerId);
        player.getCartiCurente().add(nextCard);
        playerRepository.save(player);
    }

    public List<Player> getAllPlayerFromGame(Game game){
        List<Player> playerList = new ArrayList<>();
        for (String playerId: game.getPlayersList()) {
            Player player = playerRepository.findBy_id(playerId);
            playerList.add(player);
        }
        return playerList;
    }

    public void clearCartiCurente(Game game){
        for (String playerId : game.getPlayersList()) {
            Player player = playerRepository.findBy_id(playerId);
            player.getCartiCurente().clear();
            playerRepository.save(player);

        }
    }
}
