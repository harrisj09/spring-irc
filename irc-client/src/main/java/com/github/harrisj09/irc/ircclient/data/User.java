package com.github.harrisj09.irc.ircclient.data;

import java.net.InetAddress;

public class User {

    private String userName;
    private InetAddress ip;

    public User(String userName, InetAddress ip) {
        this.userName = userName;
        this.ip = ip;
    }

    public String getUserName() {
        return userName;
    }

    public InetAddress getIp() {
        return ip;
    }
}
