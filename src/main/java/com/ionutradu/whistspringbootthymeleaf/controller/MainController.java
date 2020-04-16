package com.ionutradu.whistspringbootthymeleaf.controller;

import com.ionutradu.whistspringbootthymeleaf.model.Game;
import com.ionutradu.whistspringbootthymeleaf.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    GameRepository gameRepository;

    @GetMapping("/")
    public String index(Model model){
        List<Game> gameList = gameRepository.findAll();
        model.addAttribute("gameList", gameList);
        return "index";
    }


    @GetMapping("/login")
    public String login(){
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(){
        return "auth/registration-form";
    }
}
