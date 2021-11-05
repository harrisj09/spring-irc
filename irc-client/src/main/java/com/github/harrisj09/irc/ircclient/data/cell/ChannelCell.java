package com.github.harrisj09.irc.ircclient.data.cell;

import com.github.harrisj09.irc.ircclient.client.ClientController;
import com.github.harrisj09.irc.ircclient.data.Channel;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class ChannelCell extends ListCell<Channel> {

    final private ClientController clientController;

    public ChannelCell(ClientController clientController) {
        super();
        this.clientController = clientController;
    }

    @Override
    protected void updateItem(Channel item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            setGraphic(createChannelCell(item));
        }
    }

    private Node createChannelCell(Channel item) {
        return new HBox(new Text(item.getChannelName()));
    }
}
