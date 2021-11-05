package com.github.harrisj09.irc.ircclient.data.dtos;

import com.github.harrisj09.irc.ircclient.data.Message;
import lombok.Data;

@Data
public class MessageDTO {
    /*
[{"user":"aaaa","message":"hello","id":"3aeec6dd-a7b3-4ea3-974a-f37dc22cac03"},{"user":"jhohn","message":"heefwfesfllo  fdsfdsf","id":"730e175a-96e0-431d-a476-9193e754fff0"}]
*/
    private String user;
    private String message;
    private String id;

    public Message createMessage() {
        return new Message(user, message);
    }
}
