package com.ionutradu.whistspringbootthymeleaf.model.websocket;

public class MessageEvent {
    private String playerName;
    private String message;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageEvent(String playerName, String message) {
        this.playerName = playerName;
        this.message = message;
    }
}
