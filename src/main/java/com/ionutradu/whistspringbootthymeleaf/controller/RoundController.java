package com.ionutradu.whistspringbootthymeleaf.controller;

import com.ionutradu.whistspringbootthymeleaf.model.Game;
import com.ionutradu.whistspringbootthymeleaf.model.Player;
import com.ionutradu.whistspringbootthymeleaf.model.Round;
import com.ionutradu.whistspringbootthymeleaf.repository.GameRepository;
import com.ionutradu.whistspringbootthymeleaf.repository.RoundRepository;
import com.ionutradu.whistspringbootthymeleaf.service.CardService;
import com.ionutradu.whistspringbootthymeleaf.service.GameService;
import com.ionutradu.whistspringbootthymeleaf.service.PlayerService;
import com.ionutradu.whistspringbootthymeleaf.service.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/game")
public class RoundController {

    @Autowired
    GameService gameService;

    @Autowired
    RoundService roundService;

    @Autowired
    PlayerService playerService;

    @Autowired
    CardService cardService;

//    @GetMapping("/play/{gameId}/{roundNr}")
//    public String play(@PathVariable String gameId, @PathVariable int roundNr, Model model) {
//        Game game = gameService.findById(gameId);
//        Round round = roundService.getRoundByRoundNr(game, roundNr);
//        roundService.distribuieCarti(round);
////            distribuire.calcTotalVotate();
////            distribuire.genereazaMaini();
////            gameService.jucatorDaCarte(game);
//
//        List<Player> playerList = playerService.getAllPlayerFromGame(game);
//        model.addAttribute("players", playerList);
//        model.addAttribute("round", round);
//        model.addAttribute("game", game);
//        model.addAttribute("roundNr", roundNr);
//        model.addAttribute("cardService", cardService);
//        return "game/play";
//    }

    @PostMapping("votate")
    public String votate(@RequestParam int roundNr, @RequestParam int nrVotate, @RequestParam String gameId, Authentication authentication) {
        Game game = gameService.findById(gameId);
        Player player = playerService.getCurentPlayer(authentication);
        Round round = roundService.getRoundByRoundNr(game, roundNr);
        roundService.jucatorCateVotezi(round, player, nrVotate);
            return "redirect:/game/" + game.get_id() + "/" + roundNr;
    }

}
