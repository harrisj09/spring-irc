package com.github.harrisj09.irc.ircserver.model;

import com.github.harrisj09.irc.ircserver.model.data.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserModel {
    private List<User> users;

    public UserModel() {
        this.users = new ArrayList<>();
    }

    // When user connects check
    public List<User> getUsers() {
        return users;
    }

    public void kickUser(String user) {
        users.remove(user);
    }
}
