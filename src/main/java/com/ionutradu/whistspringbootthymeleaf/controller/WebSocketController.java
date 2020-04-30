package com.ionutradu.whistspringbootthymeleaf.controller;

import com.ionutradu.whistspringbootthymeleaf.model.Round;
import com.ionutradu.whistspringbootthymeleaf.model.websocket.MessageEvent;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/mesaje")
    @SendTo("/topic/message")
    public MessageEvent getMessage(MessageEvent messageEvent) throws InterruptedException {

        Thread.sleep(1000);

        return messageEvent;
    }
}
