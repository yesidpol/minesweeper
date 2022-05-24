package mines.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import mines.gui.controller.MainController;


public class MainGui extends Application {

    public static void mainGui(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {    
        MainController controller = new MainController(primaryStage);

        controller.initialize();
    }
}
