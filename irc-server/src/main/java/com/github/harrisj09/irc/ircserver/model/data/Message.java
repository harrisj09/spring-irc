package com.github.harrisj09.irc.ircserver.model.data;

import java.util.UUID;

public class Message {


    private String user;
    private String message;
    private UUID id;

    public Message(String user, String message) {
        this.user = user;
        this.message = message;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }
}
