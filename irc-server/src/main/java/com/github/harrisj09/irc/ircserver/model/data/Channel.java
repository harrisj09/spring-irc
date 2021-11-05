package com.github.harrisj09.irc.ircserver.model.data;

import java.util.LinkedList;
import java.util.List;

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

    public void addMessage(String user, String message) {
        messageList.add(new Message(user, message));
    }

    public List<Message> getLatestMessages() {
        return messageList;
    }

    /*public String chatRoom() {
        Stream<Object> result = messageList.stream()
                .map(n -> n)
        return "";
    }*/
}
