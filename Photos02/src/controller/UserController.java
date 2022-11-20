package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Admin;
import model.*;

/**
 * This is the class that lets the user controls the AlbumList scene.
 * @author Elizabeth Lam
 * @author Himani Patel
 */
public class UserController implements LogOutController, Initializable {
	
	@FXML
	public ListView<String> listview;
	@FXML
	public Button openAlbum, addAlbum, deleteAlbum, renameAlbum, searchPhotos, logout;

	public static ArrayList<String> albumListNames = new ArrayList<>();
	public ObservableList<String> obsList;
	public static Admin admin = Main.driver;
	public static User currentUser = admin.getCurrent();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		//update();
		//currentUser = admin.getCurrent();
		//System.out.println(currentUser.username);
		
	}
	public void update() {
		albumListNames.clear();
		for (int i = 0; i < currentUser.getAlbumList().size() ; i++) {
			albumListNames.add(currentUser.getAlbumList().get(i).getAlbumName());
		}
		listview.refresh();
		obsList = FXCollections.observableArrayList(albumListNames);
		listview.setItems(obsList);
		listview.refresh();
	}
	public void addAlbumAction(ActionEvent event) {
		
	}
	public void deleteAlbumAction(ActionEvent event) {
		
	}
	public void renameAlbumAction(ActionEvent event) {
		
	}
	public void logOut(ActionEvent event) throws IOException {
		LogOut(event);
	}

}
