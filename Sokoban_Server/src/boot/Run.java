package boot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.SokobanServer;
import view.GUIController;
import viewModel.SokobanServerViewModel;

public class Run extends Application{

private static final int port = 5555;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			// Main Window Loading
			FXMLLoader mainWindowloader = new FXMLLoader(getClass().getResource("AdminWindow.fxml"));
			BorderPane mainWindowroot = (BorderPane) mainWindowloader.load();

			// Controllers catching
			GUIController view = (GUIController) mainWindowloader.getController();
			SokobanServer model = new SokobanServer(port);
			SokobanServerViewModel viewModel = new SokobanServerViewModel(model);	
			view.setViewModel(viewModel);
			model.addObserver(viewModel);
			viewModel.addObserver(view);
			
			// Main Window
			Scene mainWindowScene = new Scene(mainWindowroot); 
			
			mainWindowScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(mainWindowScene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args); // application launch
	}
}
