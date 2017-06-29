package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.ClientInfo;

public class InfoWindowController {
	@FXML
	Text clientIp;
	@FXML
	Text clientId;
	@FXML
	Text clientState;
	@FXML
	Text clientCurrentLevel;
	@FXML
	Text clientPort;
	@FXML
	Button deleteButton;

	public Button getDeleteButton() {
		return deleteButton;
	}

	public Text getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp.setText(clientIp);
	}

	public Text getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId.setText(clientId);
	}

	public Text getClientState() {
		return clientState;
	}

	public void setClientState(String clientState) {
		this.clientState.setText(clientState);
	}

	public Text getClientCurrentLevel() {
		return clientCurrentLevel;
	}

	public void setClientCurrentLevel(String clientCurrentLevel) {
		this.clientCurrentLevel.setText(clientCurrentLevel);
	}

	public Text getClientPort() {
		return clientPort;
	}

	public void setClientPort(String clientPort) {
		this.clientPort.setText(clientPort);
	}

}
