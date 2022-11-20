package controller;

import java.io.File;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * 
 * This is an interface that will let the user log out and returns to the Log In Screen.
 * @author Himani Patel
 * @author Elizabeth Lam
 *
 */
public interface LogOutController{
	
	
	/**
	 * This method will log out the user after confirmation.
	 * @param input
	 * @throws IOException
	 */
	default void LogOut(ActionEvent event) throws IOException {
		Alert a = new Alert(AlertType.CONFIRMATION);
		a.setTitle("Logout Confirmation");
		a.setHeaderText(null);
		a.setContentText("Are you sure you want to logout?");
		
		Optional<ButtonType> result = a.showAndWait();
		if (result.get() == ButtonType.OK) { 
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/LogIn.fxml"));
			Parent sceneManager = (Parent) fxmlLoader.load();
			Scene adminScene = new Scene(sceneManager);
			Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			appStage.setScene(adminScene);
			appStage.show();
		} else {
			return;
		}
		
		
	}

}
