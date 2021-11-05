package com.github.harrisj09.irc.ircclient.data;

import java.util.LinkedList;

public class Channel {
    private String channelName;
    private LinkedList<Message> messageList;

    public Channel(String channelName) {
        this.channelName = channelName;
        this.messageList = new LinkedList<>() {
            @Override
            public boolean add(Message message) {
                if(this.size() > 50) {
                    this.removeLast();
                }
                return super.add(message);
            }
        };
    }

    public String getChannelName() {
        return channelName;
    }

    public LinkedList<Message> getMessageList() {
        return messageList;
    }
}
