package com.github.harrisj09.irc.ircclient.data.cell;

import com.github.harrisj09.irc.ircclient.client.ClientController;
import com.github.harrisj09.irc.ircclient.data.User;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class UserCell extends ListCell<User> {

    private ClientController clientController;

    public UserCell(ClientController clientController) {
        super();
        this.clientController = clientController;
    }

    @Override
    protected void updateItem(User item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            setGraphic(createUserCell(item));
        }
    }

    private Node createUserCell(User item) {
        return new HBox(new Text(item.getUserName()));
    }
}
