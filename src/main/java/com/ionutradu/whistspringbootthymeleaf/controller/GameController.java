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

    @GetMapping("/{gameId}")
    public String start(@PathVariable String gameId, Authentication authentication, Model model){
        Game game = gameService.findById(gameId);
        Round curentRound = roundService.getCurentRoundByGame(game);
        Round recentRound = roundService.getRecentRoundByGame(game);
        roundService.distribuieCarti(curentRound);
        Card atu = cardService.getAtu(curentRound);

        Player curentPlayer = playerService.getCurentPlayer(authentication);
//        List<Card> curentCards = cardService.getCurentCards(curentPlayer);

        boolean eRandulMeu = roundService.eRandulMeu(game, curentRound.getMapVotate(), curentPlayer);

        Hand curentHand = handService.getCurentHand(curentRound);
        Hand recentHand = handService.getRecentHand(curentRound);
        Card sendedCard = handService.getSendedCard(curentHand, curentPlayer);

        boolean eRandulMeuSaDauCarte = roundService.eRandulMeu(game, curentHand.getCartiJucatori(), curentPlayer);
        List<Player> jucatoriCareAuVotat = roundService.jucatoriCareAuVotat(curentRound);
        List<Player> jucatoriCareAuDatCarte = roundService.jucatoriCareAuDatCarte(curentHand);
        List<Card> curentCards = handService.getCartiDisponibile(curentPlayer, curentHand);
        List<Card> restCards = handService.getRestCarti(curentPlayer, curentCards);

        model.addAttribute("curentGame", game);
        model.addAttribute("atu", atu);
        model.addAttribute("curentRound", curentRound);
        model.addAttribute("player", curentPlayer);
        model.addAttribute("curentCardList", curentCards);
        model.addAttribute("restCarti", restCards);
        model.addAttribute("potSaVotez", eRandulMeu);
        model.addAttribute("curentHand", curentHand);
        model.addAttribute("sendedCards", sendedCard);
        model.addAttribute("potSaDauCarte", eRandulMeuSaDauCarte);
        model.addAttribute("jucatoriCareAuVotat", jucatoriCareAuVotat);
        model.addAttribute("playerService", playerService);
        model.addAttribute("gameService", gameService);
        model.addAttribute("roundService", roundService);
        model.addAttribute("handService", handService);
        model.addAttribute("cardService", cardService);
        model.addAttribute("recentHand", recentHand);
        model.addAttribute("recentRound", recentRound);
        model.addAttribute("jucatoriCareAuDatCarte", jucatoriCareAuDatCarte);
        return "game/play";
    }
}
