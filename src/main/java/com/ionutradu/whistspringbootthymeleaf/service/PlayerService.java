package com.ionutradu.whistspringbootthymeleaf.service;

import com.ionutradu.whistspringbootthymeleaf.documents.Game;
import com.ionutradu.whistspringbootthymeleaf.documents.Player;
import com.ionutradu.whistspringbootthymeleaf.documents.Round;
import com.ionutradu.whistspringbootthymeleaf.repository.GameRepository;
import com.ionutradu.whistspringbootthymeleaf.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    RoundService roundService;

    public void jucatorPreiaCartea(Player player, String nextCardId) {
            player.getCartiCurente().add(nextCardId);
            playerRepository.save(player);
    }

    public List<Player> getAllPlayerFromGame(Game game){
        List<Player> playerList = new ArrayList<>();
        for (String playerId: game.getIdJucatori()) {
            Player player = playerRepository.findById(playerId);
            playerList.add(player);
        }
        return playerList;
    }

    public void clearCartiCurente(Game game){
        for (String playerId : game.getIdJucatori()) {
            Player player = playerRepository.findById(playerId);
            player.getCartiCurente().clear();
            playerRepository.save(player);

        }
    }
}
