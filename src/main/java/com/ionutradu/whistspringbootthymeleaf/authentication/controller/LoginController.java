package com.ionutradu.whistspringbootthymeleaf.authentication.controller;

import com.ionutradu.whistspringbootthymeleaf.authentication.model.User;
import com.ionutradu.whistspringbootthymeleaf.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/showlogin")
    public String showLoginPage(){
        return "login";
    }



}
