package com.github.harrisj09.irc.ircclient.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.harrisj09.irc.ircclient.data.Channel;
import com.github.harrisj09.irc.ircclient.data.Message;
import com.github.harrisj09.irc.ircclient.data.User;
import com.github.harrisj09.irc.ircclient.data.handlers.DataRetrieveHandler;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class ClientController {
    private ClientModel clientModel;
    private Channel currentChannel;
    private DataRetrieveHandler dataRetrieveHandler;

    public ClientController(ClientModel clientModel, DataRetrieveHandler dataRetrieveHandler) {
        this.clientModel = clientModel;
        this.dataRetrieveHandler = dataRetrieveHandler;
        currentChannel = null;
    }

    public List<Channel> grabChannels() throws URISyntaxException, JsonProcessingException {
        String data = dataRetrieveHandler.fetchData(clientModel.getIp(), clientModel.getPort(), "connect/servers");
        return Arrays.asList(dataRetrieveHandler.grabChannels(data));
    }

    public void changeChannel(Channel channel) {
        currentChannel = channel;
    }

    public List<User> grabUsers() throws URISyntaxException, JsonProcessingException {
        String data = dataRetrieveHandler.fetchData(clientModel.getIp(), clientModel.getPort(), "connect/users");
        return Arrays.asList(dataRetrieveHandler.grabUsers(data));
    }

    public List<Message> grabMessages(Channel currentChannel) throws URISyntaxException, JsonProcessingException {
        String data = dataRetrieveHandler.fetchData(clientModel.getIp(), clientModel.getPort(), "channels/" + currentChannel.getChannelName() + "/latest");
        return Arrays.asList(dataRetrieveHandler.grabMessages(data));
    }

    public void sendMessage(String channelName, String message) throws URISyntaxException {

        HttpRequest build = HttpRequest.newBuilder().GET().uri(
                new URI("http://" + clientModel.getIp() + ":" + clientModel.getPort() + "/channels/" + channelName + "/" + clientModel.getUsername() + "/" + message)).build();
        HttpResponse<String> send;
        try {
            send = HttpClient.newBuilder().build().send(build, HttpResponse.BodyHandlers.ofString());
            if (send.statusCode() == 200) {
            }
            if (send.statusCode() == 409) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Username already taken");
                alert.show();
            }
            // Add if statement for CONFLICT HttpStatus
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String removeSpaces(String message) {
        while (message.indexOf(" ") != -1) {

        }
        return message;
    }
}
