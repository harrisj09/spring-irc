package com.github.harrisj09.irc.ircserver.model.data;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class User {

    private String userName;
    private InetAddress ip;

    public User(String userName, String ip) throws UnknownHostException {
        this.userName = userName;
        this.ip = InetAddress.getByName(ip);
    }

    public String getUserName() {
        return userName;
    }

    public InetAddress getIp() {
        return ip;
    }
}
