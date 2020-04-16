package com.ionutradu.whistspringbootthymeleaf.controller;


import com.ionutradu.whistspringbootthymeleaf.model.*;
import com.ionutradu.whistspringbootthymeleaf.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/game")
public class GameController {

    @Autowired
    GameService gameService;

    @Autowired
    RoundService roundService;

    @Autowired
    PlayerService playerService;

    @Autowired
    CardService cardService;

    @Autowired
    HandService handService;

    @PostMapping("/newgame/{playersNr}")
    public RedirectView newGame(@PathVariable int playersNr, Authentication authentication){
        Game game = new Game(playersNr);
        gameService.save(game);
        gameService.joinPlayer(game.get_id(), authentication);
        return new RedirectView("/game/" + game.get_id() + "/wait");
    }

    @PostMapping("/{gameId}/join")
    public String join(@PathVariable String gameId, Authentication authentication){
        Game game = gameService.findById(gameId);
        gameService.joinPlayer(game.get_id(), authentication);
        return "redirect:/game/" + game.get_id() + "/wait";

    }


    @GetMapping("/{gameId}/wait")
    public String wait(@PathVariable String gameId, Model model){
        Game game = gameService.findById(gameId);
        List<Player> playerList = playerService.getAllPlayerFromGame(game);
        model.addAttribute(game);
        model.addAttribute("Players", playerList);

        return "game/waitforplayers";
    }

    @GetMapping("/{gameId}/{roundNr}")
    public String start(@PathVariable String gameId, @PathVariable int roundNr, Authentication authentication, Model model){
        Game game = gameService.findById(gameId);
        Round curentRound = roundService.getRoundByRoundNr(game, roundNr);
        roundService.distribuieCarti(curentRound);
        Card atu = cardService.getAtu(curentRound);

        Player curentPlayer = playerService.getCurentPlayer(authentication);
        List<Card> curentCards = cardService.getCurentCards(curentPlayer);

        boolean eRandulMeu = roundService.eRandulMeu(game, curentRound, curentPlayer);

        int curentHandNr = curentRound.getCurentHand();
        Hand curentHand = handService.getCurentHand(curentRound, curentHandNr);
        Card sendedCard = handService.getSendedCard(curentHand, curentPlayer);

                model.addAttribute("atu", atu);
        model.addAttribute("curentRound", curentRound);
        model.addAttribute("roundNumber", roundNr);
        model.addAttribute("player", curentPlayer);
        model.addAttribute("curentCardList", curentCards);
        model.addAttribute("potSaVotez", eRandulMeu);
        model.addAttribute("curentHand", curentHand);
        model.addAttribute("sendedCards", sendedCard);
        return "game/play";
    }




}
