package controller;

import java.io.File;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Optional;

import application.Photos;
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
	public static Admin driver = Photos.driver;
	
	public Stage stage;
	public Scene scene;
	public Parent root;

	/**
	 * This method will return to a new scene depending on the user that had logged in.
	 * @param event
	 * @throws IOException
	 * @throws URISyntaxException 
	 */
	@FXML
	public void LogIn(ActionEvent event) throws IOException, URISyntaxException {
	
		String name = username.getText().toLowerCase().trim();
		
		if(name.equals(admin)) {
			root = FXMLLoader.load(getClass().getResource("/view/Admin.fxml"));
			stage = (Stage)((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} else if (name.equals("stock") && !driver.stockAdded) {
			driver.stockAdded = true;
			//System.out.println("helo");
			String currentPath = System.getProperty("user.dir");
			User stock = driver.getUser(0);
			Album stockImg = new Album("stock");
			stock.addAlbum(stockImg);
			char x = File.separatorChar;
			String relativePath = x+"data"+x;
			String [] stockFilePaths = new String [] {"cat.jpeg", "dog.jpg", "duck.jpg", "fox.jpg", "frog.jpg"};
			String [] captions = new String [] {"cat", "dog", "duck", "fox", "frog"};
			for(int i = 0; i< captions.length; i++) {
				Photo p = new Photo(i);
				p.setFile(new File(currentPath+relativePath+stockFilePaths[i]));
				p.setFilePath(currentPath+relativePath+stockFilePaths[i]);
				p.setCaption(captions[i]);
				stockImg.addPhoto(p);
			}
			driver.setCurrent(stock);
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/AlbumList.fxml"));
			//root = FXMLLoader.load(getClass().getResource("/view/AlbumList.fxml"));
			root = loader.load();
			
			UserController userC = loader.getController();
			
			scene = new Scene(root);
			stage = (Stage)((Node) event.getSource()).getScene().getWindow();
			
			userC.start(stage, stock);
			
			stage.setScene(scene);
			stage.show();
		}
			else if (driver.userExists(name)) {
			User current = driver.getUser(name);
			driver.setCurrent(current);
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/AlbumList.fxml"));
			//root = FXMLLoader.load(getClass().getResource("/view/AlbumList.fxml"));
			root = loader.load();
			
			UserController userC = loader.getController();
			
			scene = new Scene(root);
			stage = (Stage)((Node) event.getSource()).getScene().getWindow();
			
			userC.start(stage, current);
			
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
			return;
		}
	}

}
