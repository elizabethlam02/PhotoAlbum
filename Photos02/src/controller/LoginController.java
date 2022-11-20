package controller;

import java.io.File;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import application.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
/**
 * 
 *  This is the class that controls the Log In scene.
 * @author Himani Patel
 * @author Elizabeth Lam
 *
 */
public class LoginController {
	
	//@FXML public Button logIn;
	@FXML public TextField username;

	public final String admin = "admin";
	public static Admin driver = Main.driver;
	
	public Stage stage;
	public Scene scene;
	public Parent root;
	
	@FXML
	public void LogIn(ActionEvent event) throws IOException {
	
		String name = username.getText().toLowerCase().trim();
		
		if(name.equals(admin)) {
			root = FXMLLoader.load(getClass().getResource("/view/Admin.fxml"));
			stage = (Stage)((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} else if (driver.userExists(name)) {
			User current = driver.getUser(name);
			driver.setCurrent(current);
			root = FXMLLoader.load(getClass().getResource("/view/AlbumList.fxml"));
			stage = (Stage)((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Login Issue");
			alert.setHeaderText("Please enter a valid username");
			Optional<ButtonType> buttonClicked=alert.showAndWait();
			if (buttonClicked.get()==ButtonType.OK) {
				alert.close();
			}
			else {
				alert.close();
			}
			
		}
	}

}
