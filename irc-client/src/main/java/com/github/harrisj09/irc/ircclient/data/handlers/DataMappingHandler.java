package com.github.harrisj09.irc.ircclient.data.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.harrisj09.irc.ircclient.data.Channel;
import com.github.harrisj09.irc.ircclient.data.Message;
import com.github.harrisj09.irc.ircclient.data.User;
import com.github.harrisj09.irc.ircclient.data.dtos.MessageDTO;
import com.github.harrisj09.irc.ircclient.data.dtos.UserDTO;

import java.util.stream.Stream;

public class DataMappingHandler {
    /*
TODO Return list instead of arrays
 */
    public Channel[] createChannelArray(String channels) throws JsonProcessingException {
        if (channels != null) {
            String[] channelString = new ObjectMapper().readValue(channels, String[].class);
            Channel[] channelArray = new Channel[channelString.length];
            for (int i = 0; i < channelArray.length; i++) {
                channelArray[i] = new Channel(channelString[i]);
            }
            return channelArray;
        }
        return new Channel[0];
    }

    public User[] createUserArray(String users) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        UserDTO[] userDTOS = objectMapper.readValue(users, UserDTO[].class);
        return Stream.of(userDTOS).map(UserDTO::createUser).toArray(User[]::new);
    }

    public Message[] createMessageArray(String messages) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        MessageDTO[] messageDTOS = objectMapper.readValue(messages, MessageDTO[].class);
        return Stream.of(messageDTOS).map(MessageDTO::createMessage).toArray(Message[]::new);
    }
}
