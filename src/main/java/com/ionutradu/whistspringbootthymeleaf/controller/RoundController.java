package com.ionutradu.whistspringbootthymeleaf.controller;

import com.ionutradu.whistspringbootthymeleaf.documents.Game;
import com.ionutradu.whistspringbootthymeleaf.documents.Player;
import com.ionutradu.whistspringbootthymeleaf.documents.Round;
import com.ionutradu.whistspringbootthymeleaf.repository.GameRepository;
import com.ionutradu.whistspringbootthymeleaf.repository.RoundRepository;
import com.ionutradu.whistspringbootthymeleaf.service.CardService;
import com.ionutradu.whistspringbootthymeleaf.service.PlayerService;
import com.ionutradu.whistspringbootthymeleaf.service.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/game")
public class RoundController {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    RoundRepository roundRepository;

    @Autowired
    RoundService roundService;

    @Autowired
    PlayerService playerService;

    @Autowired
    CardService cardService;

    @GetMapping("/play/{gameId}/{roundNr}")
    public String play(@PathVariable String gameId, @PathVariable int roundNr, Model model){
        Game game = gameRepository.findById(gameId).orElse(null);
        String roundId = game.getIdDistribuiri().get(roundNr);
        Round round = roundRepository.findById(roundId);


        roundService.distribuieCarti(round);
        roundService.jucatorCateVotezi(round);
//            distribuire.calcTotalVotate();
//            distribuire.genereazaMaini();
//            gameService.jucatorDaCarte(game);

        List<Player> playerList = playerService.getAllPlayerFromGame(game);
        model.addAttribute("players", playerList);
        model.addAttribute("round", round);
        model.addAttribute("game", game);
        model.addAttribute("roundNr", roundNr);
        model.addAttribute("cardService", cardService);
        return "game/play";
    }
}
