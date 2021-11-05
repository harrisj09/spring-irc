package com.github.harrisj09.irc.ircclient.client.Connection;

import javafx.scene.control.Alert;
import lombok.Data;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
@Data
public class ConnectionHandler {

    // Not sure if these will be used
    private String channels;
    private String ip;
    private String port;
    private String username;

    public boolean  canConnect(String ip, String port, String username) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest build = HttpRequest.newBuilder().GET().uri(
                new URI("http://" + ip + ":" + port + "/connect/" + username)).build();
        HttpResponse<String> send;
        try {
            send = HttpClient.newBuilder().build().send(build, HttpResponse.BodyHandlers.ofString());
            if (send.statusCode() == 200) {
                // fix this
                setChannels(send.body());
                setIp(ip);
                setPort(port);
                setUsername(username);
                return true;
            }
            if (send.statusCode() == 409) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Username already taken");
                alert.show();
            }
            // Add if statement for CONFLICT HttpStatus
        } catch (ConnectException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Failed to connect");
            alert.show();
        }
        return false;
    }

    public void sendMessage(String ip, String port, String username, String message) {

    }
}
