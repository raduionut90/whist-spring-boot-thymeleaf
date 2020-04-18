package com.ionutradu.whistspringbootthymeleaf.controller;

import com.ionutradu.whistspringbootthymeleaf.model.Game;
import com.ionutradu.whistspringbootthymeleaf.model.Hand;
import com.ionutradu.whistspringbootthymeleaf.model.Player;
import com.ionutradu.whistspringbootthymeleaf.model.Round;
import com.ionutradu.whistspringbootthymeleaf.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/round")
public class RoundController {

    @Autowired
    GameService gameService;

    @Autowired
    RoundService roundService;

    @Autowired
    PlayerService playerService;

    @Autowired
    CardService cardService;

    @Autowired
    private HandService handService;

    @PostMapping("votate")
    public String votate(@RequestParam int nrVotate, @RequestParam String gameId, Authentication authentication) {
        Game game = gameService.findById(gameId);
        Player player = playerService.getCurentPlayer(authentication);
        Round round = roundService.getCurentRoundByGame(game);
        roundService.jucatorCateVotezi(round, player, nrVotate);
            return "redirect:/game/" + game.get_id();
    }

    @PostMapping("sendCard")
    public String sendCard(@RequestParam String gameId, @RequestParam String cardId, Authentication authentication) {
        Game game = gameService.findById(gameId);
        Player player = playerService.getCurentPlayer(authentication);
        Round round = roundService.getCurentRoundByGame(game);
        Hand curentHand = handService.getCurentHand(round);
        handService.sendCard(player, cardId, curentHand);
        handService.verificaManaCompleta(game, curentHand, round);
        roundService.verificaRoundaCompleta(game, round);
        return "redirect:/game/" + game.get_id();
    }
}
