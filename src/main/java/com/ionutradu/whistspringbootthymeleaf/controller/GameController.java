package com.ionutradu.whistspringbootthymeleaf.controller;


import com.ionutradu.whistspringbootthymeleaf.model.Game;
import com.ionutradu.whistspringbootthymeleaf.repository.GameRepository;
import com.ionutradu.whistspringbootthymeleaf.repository.RoundRepository;
import com.ionutradu.whistspringbootthymeleaf.service.GameService;
import com.ionutradu.whistspringbootthymeleaf.service.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import sun.text.normalizer.NormalizerBase;

import java.security.Principal;

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

    @PostMapping("/newgame/{playersNr}")
    public RedirectView newGame(@PathVariable int playersNr, RedirectAttributes redirectAttributes, Authentication authentication){
        Game game = new Game(playersNr);
        gameRepository.save(game);
        gameService.joinPlayer(game.getId(), authentication);

//        gameService.genereazaJucatori(game);
//        gameService.genereazaCarti(game);
//        gameService.genereazaRunde(game);
//        gameService.genereazaMaini(game);
//        gameRepository.save(game);

        redirectAttributes.addFlashAttribute(game);
      //return "/game/waitforplayers";
        return new RedirectView("/game/wait");
    }


    @GetMapping("/wait")
    public ModelAndView wait(@ModelAttribute Game game){
        ModelAndView model=new ModelAndView("/game/waitforplayers");
        model.addObject("game", game);
        return model;
    }




}
