package com.ionutradu.whistspringbootthymeleaf.controller;


import com.ionutradu.whistspringbootthymeleaf.documents.Game;
import com.ionutradu.whistspringbootthymeleaf.documents.Round;
import com.ionutradu.whistspringbootthymeleaf.repository.GameRepository;
import com.ionutradu.whistspringbootthymeleaf.repository.RoundRepository;
import com.ionutradu.whistspringbootthymeleaf.service.GameService;
import com.ionutradu.whistspringbootthymeleaf.service.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/newgame/{playersNr}")
    public String newGame(@PathVariable int playersNr, Model model){
        Game game = new Game(playersNr);
        gameService.genereazaJucatori(game);
        gameService.genereazaCarti(game);
        gameService.genereazaRunde(game);
        gameService.genereazaMaini(game);
        gameRepository.save(game);
        model.addAttribute("game", game);
        return "game/newgame";
    }
    
    





}
