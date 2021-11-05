package com.github.harrisj09.irc.ircclient;

import com.github.harrisj09.irc.ircclient.client.ClientController;
import com.github.harrisj09.irc.ircclient.client.ClientModel;
import com.github.harrisj09.irc.ircclient.client.ClientView;
import com.github.harrisj09.irc.ircclient.client.Connection.ConnectionHandler;
import com.github.harrisj09.irc.ircclient.data.handlers.DataRetrieveHandler;
import com.github.harrisj09.irc.ircclient.login.LoginView;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

import static javafx.application.Application.launch;


public class Main extends Application {

	private ConnectionHandler connectionHandler;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Scene scene = new Scene(new Pane());
		primaryStage.setScene(scene);

		LoginView startView = new LoginView(primaryStage);

		ScreenController screenController = new ScreenController(scene);
		screenController.addScreen("start", startView.createLayout());
		screenController.activate("start");

		primaryStage.setHeight(600);
		primaryStage.setWidth(600);

		EventHandler<MouseEvent> eventHandler = e -> {
			ClientModel model = new ClientModel(startView.getIp().getText(), startView.getPort().getText(), startView.getUsername().getText(), connectionHandler = new ConnectionHandler());
			ClientController controller = new ClientController(model, new DataRetrieveHandler());
			ClientView view = new ClientView(controller, primaryStage);
			connectionHandler = new ConnectionHandler();
			try {
				if (model.canConnect()) {
					model.connectToServer();
					screenController.addScreen("client", view.getLayout());
					screenController.activate("client");
					primaryStage.setMaximized(true);
				} else {
					Alert alert = new Alert(Alert.AlertType.ERROR, "Trouble Connecting");
					alert.show();
				}
			} catch (URISyntaxException | IOException | InterruptedException uriSyntaxException) {
				uriSyntaxException.printStackTrace();
			}
		};
		startView.getButton().addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
		primaryStage.show();
	}
}
