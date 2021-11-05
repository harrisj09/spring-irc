package com.github.harrisj09.irc.ircclient.data.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.harrisj09.irc.ircclient.data.Channel;
import com.github.harrisj09.irc.ircclient.data.Message;
import com.github.harrisj09.irc.ircclient.data.User;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataRetrieveHandler {
    // http://localhost:8080/createchannel/{{name}}
    private String server = "http://";
    private DataMappingHandler dataMappingHandler;
    private static final Logger logger = Logger.getLogger(DataRetrieveHandler.class.getName());

    public DataRetrieveHandler() {
        this.dataMappingHandler = new DataMappingHandler();
    }

    public String fetchData(String ip, String port, String endpoint) throws URISyntaxException {
        this.server += ip + ":" + port + "/" + endpoint;
        HttpRequest build = HttpRequest.newBuilder().GET().uri(new URI(server)).build();
        HttpResponse<String> send;
        try {
            send = HttpClient.newBuilder().build().send(build, HttpResponse.BodyHandlers.ofString());
            logger.log(Level.INFO, "Data: " + send.body() + "\nEndpoint: " + endpoint);
            // Successful
            if (send.statusCode() == 200) {
                this.server = "http://";
                return send.body();
            }
            if (send.statusCode() == 409) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Conflict occurred");
                alert.show();
            }
        } catch (IOException | InterruptedException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Failed to connect");
            alert.show();
        }
        this.server = "http://";
        return null;
    }

    private HttpResponse<String> getResponse(HttpRequest build) throws IOException, InterruptedException {
        return HttpClient.newBuilder().build().send(build, HttpResponse.BodyHandlers.ofString());
    }

    public Channel[] grabChannels(String data) throws JsonProcessingException {
        return dataMappingHandler.createChannelArray(data);
    }

    public User[] grabUsers(String data) throws JsonProcessingException {
        return dataMappingHandler.createUserArray(data);
    }

    public Message[] grabMessages(String data) throws JsonProcessingException {
        return dataMappingHandler.createMessageArray(data);
    }
}
