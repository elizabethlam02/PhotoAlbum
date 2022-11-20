package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.*;
/**
 * 
 *  This is the class that controls the Admin class
 * @author Himani Patel
 * @author Elizabeth Lam
 *
 */
public class AdminController implements LogOutController, Initializable {
	
	@FXML
	public ListView<String> listview;
	@FXML
	public Button logout;
	@FXML
	private TextField user;
	@FXML
	private Button addUser;
	@FXML
	private Button deleteUser;

	
	public static ArrayList<String> userlist = new ArrayList<>();
	public ObservableList<String> obsList;
	public static Admin admin = Main.driver;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		if (admin.getUsers().size() == 0) {
		} else {
			update();
		}

	}
	public void update() {
		userlist.clear();
		for (int i = 0; i < admin.getUsers().size(); i++) {
			userlist.add(admin.getUsers().get(i).getUsername());
		}
		listview.refresh();
		obsList = FXCollections.observableArrayList(userlist);
		listview.setItems(obsList);
		listview.refresh();
	}
	public void logOut(ActionEvent event) throws IOException {
		LogOut(event);
	}
	public void addUserAction(ActionEvent event) throws IOException {
		String username = user.getText().toLowerCase().trim();
		if(username.isEmpty() || username == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("WARNING");
			alert.setContentText("Field is Empty. Please enter a username");
			alert.showAndWait();
			return;
		} else if(admin.userExists(username)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("WARNING");
			alert.setContentText("User already exists");
			alert.showAndWait();
			return;
		} else if (username.equals("admin")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("WARNING");
			alert.setContentText("Admin is not available.");
			alert.showAndWait();
			return;
		} else {
			String shown = user.getText().trim();
			admin.createUser(username);
			obsList.add(username);
			listview.getItems().add(shown);
			update();
			listview.getSelectionModel().select(obsList.indexOf(username));
			user.clear();
		}
		//Admin.save(admin);
		
	}
	// Delete user from Template SongLib
	public void deleteUserAction(ActionEvent event) throws IOException {
		
		//confirmation dialog 
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setHeaderText("Confirmation");
		alert.setContentText("Are you sure you want to delete this user?");
		Optional<ButtonType> answer = alert.showAndWait();
		String username = listview.getSelectionModel().getSelectedItem();
		
		// You cannot delete the admin user.
		if (username.toLowerCase().equals("admin")) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("WARNING");
			errorAlert.setContentText("You cannot delete the Admin user.");
			errorAlert.showAndWait();
		} else if (answer.get() == ButtonType.OK) {
			
			int index = obsList.indexOf(username);
			obsList.remove(index);
			
			if (obsList.size() == 0) {
				user.setText("");
			} else if (index == obsList.size()){
				listview.getSelectionModel().select(index - 1);
				String current = listview.getSelectionModel().getSelectedItem();
				user.setText(current);
			} else {
				listview.getSelectionModel().select(index);
				String curr = listview.getSelectionModel().getSelectedItem();
				user.setText(curr);
			}
		}
		//Admin.save(admin);

	}


}