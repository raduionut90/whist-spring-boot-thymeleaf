package com.ionutradu.whistspringbootthymeleaf.controller;


import com.ionutradu.whistspringbootthymeleaf.model.Game;
import com.ionutradu.whistspringbootthymeleaf.model.Player;
import com.ionutradu.whistspringbootthymeleaf.repository.GameRepository;
import com.ionutradu.whistspringbootthymeleaf.repository.RoundRepository;
import com.ionutradu.whistspringbootthymeleaf.service.GameService;
import com.ionutradu.whistspringbootthymeleaf.service.PlayerService;
import com.ionutradu.whistspringbootthymeleaf.service.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/game")
public class GameController {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    GameService gameService;

    @Autowired
    RoundService roundService;

    @Autowired
    RoundRepository roundRepository;

    @Autowired
    PlayerService playerService;

    @PostMapping("/newgame/{playersNr}")
    public RedirectView newGame(@PathVariable int playersNr, Authentication authentication){
        Game game = new Game(playersNr);
        gameRepository.save(game);
        gameService.joinPlayer(game.get_id(), authentication);

//        gameService.genereazaJucatori(game);
//        gameService.genereazaCarti(game);
//        gameService.genereazaRunde(game);
//        gameService.genereazaMaini(game);
//        gameRepository.save(game);

        return new RedirectView("/game/" + game.get_id() + "/wait");
    }

    @PostMapping("/{gameId}/join")
    public String join(@PathVariable String gameId, Authentication authentication){
        Game game = gameRepository.findById(gameId).orElse(null);
        gameService.joinPlayer(game.get_id(), authentication);
        return "redirect:/game/" + game.get_id() + "/wait";

    }


    @GetMapping("/{gameId}/wait")
    public String wait(@PathVariable String gameId, Model model){
        Game game = gameRepository.findById(gameId).orElse(null);
        List<Player> playerList = playerService.getAllPlayerFromGame(game);
        model.addAttribute(game);
        model.addAttribute("Players", playerList);

        return "game/waitforplayers";
    }

    @PostMapping("/{gameId}/start")
    public String start(@PathVariable String gameId, Authentication authentication){
        Game game = gameRepository.findById(gameId).orElse(null);
        gameService.genereazaCarti(game);
        gameService.genereazaRunde(game);
        gameService.genereazaMaini(game);
        gameRepository.save(game);
        return "game/play";

    }


}
