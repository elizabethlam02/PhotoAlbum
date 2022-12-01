package controller;


import java.awt.Label;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Optional;

import application.Photos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.*;

/**
 * @author Himani Patel
 * @author Elizabeth Lam
 *
 */
public class SearchResultsController implements LogOutController {
	@FXML
	private ListView<Photo> searchResults;
	@FXML
	private Button createAlbum, logout;
	
	
	/**
	 * the observable list that will populate the listview 
	 */
	private ObservableList<Photo> obsList;
	
	
	/**
	 * the list of photos the fit the search criteria
	 */
	private ArrayList<Photo> photolist;
	@FXML
	private ImageView photo;
	/*
	@FXML
	private Label photoCaption;
	*/
	
	/**
	 * the current user 
	 */
	User currentUser;
	

	/**
	 * initializes the search results page given the parameters
	 * @param stage
	 * @param photolist
	 * @param current
	 */
	public void start(Stage stage, ArrayList<Photo> photolist, User current) {
		// TODO Auto-generated method stub
		//create an observable list from an arraylist
		currentUser = current;
		this.photolist = photolist;
		/*
		ArrayList<String> names = new ArrayList<>();
		for (Photo p: photolist) {
			names.add(p.getName());
		}
		*/
		//obsList = FXCollections.observableArrayList(names);
		obsList = FXCollections.observableArrayList(this.photolist);
		searchResults.setItems(obsList);


		searchResults.getSelectionModel().selectedIndexProperty().addListener(
				(obs, oldVal, newVal) -> 
				//showItem(mainStage)
				{
					try {
						showItem(stage);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		);
		searchResults.getSelectionModel().select(0);
		
		stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, arg0 -> {
			try {
				closeWindowEvent(arg0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}
	private void closeWindowEvent(WindowEvent event) throws IOException {
		Admin.save(Photos.driver);
	}
	
	/**
	 * displays the image
	 * @param stage
	 * @throws FileNotFoundException
	 */
	public void showItem(Stage stage) throws FileNotFoundException {
		Photo currPhoto = searchResults.getSelectionModel().getSelectedItem();
		if (currPhoto == null) {
			photo.setImage(null);
			//photoCaption.setText(null);
		} else {
			//photoCaption.setText(currPhoto.getCaption());
			File file = currPhoto.getFile();
			InputStream stream = new FileInputStream(file.getAbsolutePath());
			Image image = new Image(stream);
			photo.setImage(image);
		}
	}
	
	/**
	 * creates an album of the photos from the search results (when the create album button is pushed)
	 * @param e
	 * @throws IOException
	 */
	public void createAlbumAction(ActionEvent e) throws IOException {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Add Album");
		dialog.setHeaderText("Create a new album");
		dialog.setContentText("Please enter a name for the new album");
		
		Optional<String> name = dialog.showAndWait();
		String albumName = name.get();
		
		// Added Code to check if Album already Exists
		if (currentUser.albumExists(albumName)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setContentText("Album Name already exists.");
			alert.showAndWait();
			return;
		}
	
		if (name.isPresent() && !currentUser.albumExists(albumName)) {
			Album newAlbum = new Album(name.get());
			for (Photo p: photolist) {
				newAlbum.addPhoto(p);
			}
			currentUser.addAlbum(newAlbum);
			User.save(currentUser);
		}
	}
	
	/**
	 * This is the implementation of the LogOut interface.
	 * @param event
	 * @throws IOException
	 */
	public void logOut(ActionEvent event) throws IOException {
		LogOut(event);
	}

}
