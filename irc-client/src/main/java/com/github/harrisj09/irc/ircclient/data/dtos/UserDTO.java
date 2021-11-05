package com.github.harrisj09.irc.ircclient.data.dtos;

import com.github.harrisj09.irc.ircclient.data.User;
import lombok.Data;

import java.net.InetAddress;

@Data
public class UserDTO {
    private String userName;
    private InetAddress ip;

    public User createUser() {
        return new User(userName, ip);
    }
}
