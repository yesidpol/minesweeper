package gui;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import gui.controller.MainController;


public class MainGui extends Application {

	public static void mainGui(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {	
		MainController controller = new MainController();
		
		controller.initialize();
		
		Scene scene = new Scene((Parent)controller.getView());
		scene.getStylesheets().add("/resources/style.css");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
