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
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientController {
    private static final Logger logger = Logger.getLogger(ClientController.class.getName());
    private ClientModel clientModel;
    private DataRetrieveHandler dataRetrieveHandler;
    private Channel currentChannel;

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
        message = removeSpaces(message);
        HttpRequest build = HttpRequest.newBuilder().GET().uri(
                new URI("http://" + clientModel.getIp() + ":" + clientModel.getPort() + "/channels/" + channelName + "/" + clientModel.getUsername() + "/" + message)).build();
        HttpResponse<String> send;
        try {
            send = HttpClient.newBuilder().build().send(build, HttpResponse.BodyHandlers.ofString());
            if (send.statusCode() == 200) {
                logger.log(Level.INFO, "MESSAGE SENT");
            }
            if (send.statusCode() == 409) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Message Unable to send");
                alert.show();
            }
            // Add if statement for CONFLICT HttpStatus
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // TODO: Add rest of characters
    public String removeSpaces(String message) {
        // https://secure.n-able.com/webhelp/nc_9-1-0_so_en/content/sa_docs/api_level_integration/api_integration_urlencoding.html
        return message.trim().replace(" ", "%20").trim().replace(",", "%2C").trim().replace("?", "%3F");
    }
}