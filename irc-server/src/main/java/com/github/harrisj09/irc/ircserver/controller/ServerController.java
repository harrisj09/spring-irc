package com.github.harrisj09.irc.ircserver.controller;

import com.github.harrisj09.irc.ircserver.model.ChannelModel;
import com.github.harrisj09.irc.ircserver.model.ChatModel;
import com.github.harrisj09.irc.ircserver.model.UserModel;
import com.github.harrisj09.irc.ircserver.model.data.Channel;
import com.github.harrisj09.irc.ircserver.model.data.Message;
import com.github.harrisj09.irc.ircserver.model.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class ServerController {

    @Autowired
    private ChannelModel channelModel;
    @Autowired
    private ChatModel chatModel;
    @Autowired
    private UserModel userModel;

    @GetMapping("connect/{id}")
    public ResponseEntity<Collection<String>> connect(@PathVariable String id, HttpServletRequest request) throws UnknownHostException {
        if (userModel.getUsers().stream().anyMatch(o -> o.getUserName().equals(id))) {
            // User already exists
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        userModel.getUsers().add(new User(id, request.getRemoteAddr()));
        return new ResponseEntity<>(new ArrayList<>(channelModel.getChannels().keySet()), HttpStatus.OK);
    }

    @GetMapping("connect/servers")
    public ResponseEntity<Collection<String>> servers() {
        return new ResponseEntity<>(new ArrayList<>(channelModel.getChannels().keySet()), HttpStatus.OK);
    }

    @GetMapping("connect/users")
    public ResponseEntity<List<User>> grabUsers() {
        return new ResponseEntity<>(new ArrayList<>(userModel.getUsers()), HttpStatus.OK);
    }

    @GetMapping("channels/{channelId}/latest")
    public ResponseEntity<List<Message>> getLatestChannelMessages(@PathVariable String channelId) {
        Channel channel = channelModel.getChannel(channelId);
        if(channel == null) {
            // if this is returned have the user delete the channel
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(channel.getLatestMessages(), HttpStatus.OK);
    }

    @GetMapping("channels/{channelId}/{user}/{message}")
    public ResponseEntity<String> sendMessage(@PathVariable String channelId, @PathVariable String user, @PathVariable String message) {
        Channel channel = channelModel.getChannel(channelId);
        System.out.println("MESSAGE RECEIVED");
        if(channel == null) {
            // if this is returned have the user delete the channel
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        channel.addMessage(user, message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // TODO move this to admin and make it @PostMapping
    @GetMapping("createchannel/{name}")
    public ResponseEntity<String> createChannel(@PathVariable String name) {
        Channel channel = channelModel.getChannel(name);
        if (channel == null) {
            channelModel.addChannel(name);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
