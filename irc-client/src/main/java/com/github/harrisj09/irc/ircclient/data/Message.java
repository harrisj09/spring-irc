package com.github.harrisj09.irc.ircclient.data;

import lombok.Data;

@Data
public class Message {
    private String user;
    private String message;
    private String ID;

    public Message(String user, String message) {
        this.user = user;
        this.message = message;
    }
}
