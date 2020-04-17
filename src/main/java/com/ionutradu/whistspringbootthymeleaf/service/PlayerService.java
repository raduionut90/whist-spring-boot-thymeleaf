package com.ionutradu.whistspringbootthymeleaf.service;

import com.ionutradu.whistspringbootthymeleaf.model.Card;
import com.ionutradu.whistspringbootthymeleaf.model.Game;
import com.ionutradu.whistspringbootthymeleaf.model.Player;
import com.ionutradu.whistspringbootthymeleaf.repository.GameRepository;
import com.ionutradu.whistspringbootthymeleaf.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

    public Player save(Player player){
        return playerRepository.save(player);
    }

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

    public Player getCurentPlayer(Authentication authentication) {
            String playerName = authentication.getName();
            return playerRepository.findByNume(playerName);
    }

    public void removeSendedCard(Player player, String cardId) {
        player.getCartiCurente().remove(cardId);
        playerRepository.save(player);
    }

    public void playerSetFlag(List<String> playersList) {
        //pt toti setez First and Last False
        for (String playerId : playersList) {
            Player player = playerRepository.findBy_id(playerId);
            player.setFirst(false);
            player.setLast(false);
            playerRepository.save(player);
        }

        //setez First
        String idFirst = playersList.get(0);
        Player first = playerRepository.findBy_id(idFirst);
        first.setFirst(true);
        playerRepository.save(first);

        //setez Last
        String idLast = playersList.get(playersList.size() - 1);
        Player last = playerRepository.findBy_id(idLast);
        last.setLast(true);
        playerRepository.save(last);
    }

    public Player findById(String playerId) {
        return playerRepository.findBy_id(playerId);
    }
}
