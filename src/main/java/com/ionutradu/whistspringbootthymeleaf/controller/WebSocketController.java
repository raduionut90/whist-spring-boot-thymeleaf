package com.ionutradu.whistspringbootthymeleaf.controller;

import com.ionutradu.whistspringbootthymeleaf.model.Round;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/game/livedata")
    @SendTo("/game/**/**")
    public Round getRoundData(Round curentRound) throws InterruptedException {

        Thread.sleep(1000);

        return curentRound;
    }
}
