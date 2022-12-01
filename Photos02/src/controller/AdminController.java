package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import application.Photos;
import model.*;
/**
 * 
 * This is the class that controls the Admin screen
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

	
	/**
	 * list of users already in program
	 */
	public static ArrayList<String> userlist = new ArrayList<>();
	
	/**
	 * the observable list used to populate the listview
	 */
	public ObservableList<String> obsList;
	
	/**
	 * the admin of the program
	 */
	public static Admin admin = Photos.driver;
	/**
	 * This method starts the scene and adds the users on listview.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		if (admin.getUsers().size() == 0) {
			update();
		} else {
			update();
		}

	}
	/**
	 *  This method updates the list view after each manipulation of input by admin user.
	 */
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
	/**
	 * This method will return the user to the Log In screen.
	 * @param event
	 * @throws IOException
	 */
	public void logOut(ActionEvent event) throws IOException {
		LogOut(event);
	}
	/**
	 * This method allows the Admin user to create a user
	 * @param event
	 * @throws IOException
	 */
	public void addUserAction(ActionEvent event) throws IOException {
		String username = user.getText().trim();
		if(username.isEmpty() || username == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("WARNING");
			alert.setContentText("Field is Empty. Please enter a username");
			alert.showAndWait();
			return;
		} else if (username.equals("admin")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("WARNING");
			alert.setContentText("Admin is not available.");
			alert.showAndWait();
			return;
		} else if(admin.userExists(username)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("WARNING");
			alert.setContentText("User already exists");
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
		try {
			//UserList.getUserList().writeApp();
			Admin.save(admin);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
		//	System.out.println("File not found");
			//return;
			e1.printStackTrace();
		}
		
	}
	/**
	 * This method allows the admin user to delete a user.
	 * @param event
	 * @throws IOException
	 */
	public void deleteUserAction(ActionEvent event) throws IOException {
		
		//confirmation dialog 
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setHeaderText("Confirmation");
		alert.setContentText("Are you sure you want to delete this user?");
		Optional<ButtonType> answer = alert.showAndWait();
		String username = listview.getSelectionModel().getSelectedItem();
		
		// You cannot delete the admin user.
		if (username.equalsIgnoreCase("admin")) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("WARNING");
			errorAlert.setContentText("You cannot delete the Admin user.");
			errorAlert.showAndWait();
			return;
		}
		if (answer.get() == ButtonType.OK) {
			
			int index = obsList.indexOf(username);
			obsList.remove(index);
			admin.deleteUser(index);
			
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
		try {
			//UserList.getUserList().writeApp();
			Admin.save(admin);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
		//	System.out.println("File not found");
			// return;
			e1.printStackTrace();
		}
	}


}