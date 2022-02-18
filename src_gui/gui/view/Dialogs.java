package gui.view;

import javafx.scene.control.Alert;

public class Dialogs {
	public static void showWinDialog() {
	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    
	    alert.setTitle("Congratulations");
	    alert.setContentText("You win!!!");
	    
	    alert.showAndWait();
	}
	
	public static void showLostDialog() {
	    Alert alert = new Alert(Alert.AlertType.ERROR);
	    
	    alert.setTitle(":'(");
	    alert.setContentText("You lost!!!");
	    
	    alert.showAndWait();
	}
}
