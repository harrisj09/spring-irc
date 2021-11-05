package com.github.harrisj09.irc.ircserver.model;

import com.github.harrisj09.irc.ircserver.model.data.Channel;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ChannelModel {
    private HashMap<String, Channel> channels = new HashMap<>();


    public Channel addChannel(String name) {
        Channel channel = new Channel(name);
        channels.put(name, channel);
        return channel;
    }

    public Map<String, Channel> getChannels() {
        return channels;
    }

    public Channel getChannel(String channelId) {
        return channels.get(channelId);
    }
}
