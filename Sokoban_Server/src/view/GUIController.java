/**
* This class responsible to manage the GUI
* @author Maayan & Eden
*/

package view;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.ClientInfo;
import viewModel.ViewModel;

public class GUIController implements View, Initializable {

	@FXML
	private TableView<ClientInfo> clientsTable;
	@FXML
	private TableColumn<ClientInfo, String> portColumn;
	@FXML
	private TableColumn<ClientInfo, String> ipColumn;
	@FXML
	private TableColumn<ClientInfo, String> idColumn;
	@FXML
	private TableColumn<ClientInfo, String> stateColumn;
	@FXML
	private TableColumn<ClientInfo, String> currentLevelColumn;
	
	@FXML
	private Button deleteButton;

	private ContextMenu context;

	private ObservableList<ClientInfo> data;
	private ViewModel viewModel;
	private ClientInfo clientInfo = null;

	// Initialization
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// TABLE
		data = FXCollections.observableArrayList();
		// list to ObservableList
		// topRecordsInitialize(_numOfRecords);

		clientsTable.getColumns().clear();
		clientsTable.setItems(data);

//		portColumn = new TableColumn<>("Client Port");
		portColumn.setCellValueFactory(new PropertyValueFactory<>("Port"));
//		portColumn.setMinWidth(100);
//		portColumn.setMaxWidth(100);

//		ipColumn = new TableColumn<>("Client IP");
		ipColumn.setCellValueFactory(new PropertyValueFactory<>("Ip"));
//		ipColumn.setMinWidth(100);
//		ipColumn.setMaxWidth(100);

//		currentLevelColumn = new TableColumn<>("Current Level");
		currentLevelColumn.setCellValueFactory(new PropertyValueFactory<>("currentLevel"));
//		currentLevelColumn.setMinWidth(200);
//		currentLevelColumn.setMaxWidth(200);

//		stateColumn = new TableColumn<>("State");
		stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
//		stateColumn.setMinWidth(100);
//		stateColumn.setMaxWidth(100);
		
//		idColumn = new TableColumn<>("Client ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//		idColumn.setMinWidth(100);
//		idColumn.setMaxWidth(100);

		clientsTable.getColumns().addAll(idColumn, portColumn, ipColumn, currentLevelColumn, stateColumn);
		clientsTable.setItems(data);

		// for Right Click on client
		context = new ContextMenu();

		MenuItem deleteItem = new MenuItem("Delete");
		MenuItem infoItem = new MenuItem("Information");

		deleteItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				viewModel.deleteClient(clientInfo.getId());
			}
		});
		infoItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showClientInfo();
			}
		});

		context.getItems().addAll(deleteItem, new SeparatorMenuItem(), infoItem);

		clientsTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {

				ClientInfo client = clientsTable.getSelectionModel().getSelectedItem();
				if (client != null) {
					// clientId = client.getId();
					clientInfo = client;
				}

				if (arg0.getButton() == MouseButton.SECONDARY && clientInfo!=null) {
					context.show(clientsTable, arg0.getScreenX(), arg0.getScreenY());
				}
			}
		});

		// Buttons
		deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if (clientInfo != null)
					viewModel.deleteClient(clientInfo.getId());
			}

		});

		// T - E - S - T //
		data.clear();
	}

	public void showClientInfo() {
		FXMLLoader infoWindowloader = new FXMLLoader(getClass().getResource("InfoWindow.fxml"));
		BorderPane infoWindowroot = null;
		try {
			infoWindowroot = (BorderPane) infoWindowloader.load();
			Scene infoWindowScene = new Scene(infoWindowroot);
			InfoWindowController iwc = infoWindowloader.getController();
			iwc.setClientCurrentLevel(clientInfo.getCurrentLevel());
			iwc.setClientId("" + clientInfo.getId());
			iwc.setClientIp(clientInfo.getIp());
			iwc.setClientPort(clientInfo.getPort());
			iwc.setClientState(clientInfo.getState());
			final Stage infoWindow = new Stage();
			infoWindow.setScene(infoWindowScene);
			infoWindow.show();
			iwc.getDeleteButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					viewModel.deleteClient(clientInfo.getId());
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void close() {
		viewModel.close();
		Platform.exit();
	}

	public ObservableList<ClientInfo> getObserverableList() {
		return this.data;
	}

	public ViewModel getViewModel() {
		return viewModel;
	}

	public void setViewModel(ViewModel viewModel) {
		this.viewModel = viewModel;
		if (viewModel != null)
			viewModel.setBindData(data);
	}

	@Override
	public void update(Observable arg0, Object arg1) {

	}

}
