package com.github.harrisj09.irc.ircclient.client;

import com.github.harrisj09.irc.ircclient.client.Connection.ConnectionHandler;
import com.github.harrisj09.irc.ircclient.data.Message;
import com.github.harrisj09.irc.ircclient.data.User;
import lombok.Data;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

@Data
public class ClientModel {
    private String ip;
    private String port;
    private String username;
    private String channels;
    private Collection<User> users;
    private Collection<Message> channelMessages;
    private boolean connectedToServer = false;
    private ConnectionHandler connectionHandler;

    public ClientModel(String ip, String port, String username, ConnectionHandler connectionHandler) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.connectionHandler = connectionHandler;
    }

    public boolean canConnect() throws InterruptedException, IOException, URISyntaxException {
        return connectionHandler.canConnect(ip, port, username);
    }

    public void connectToServer() throws IOException {
        setIp(connectionHandler.getIp());
        setPort(connectionHandler.getPort());
        setUsername(connectionHandler.getUsername());
        setChannels(connectionHandler.getChannels());
        setConnectedToServer(true);
    }

    public void setConnectedToServer(boolean connectedToServer) {
        this.connectedToServer = connectedToServer;
    }
}
