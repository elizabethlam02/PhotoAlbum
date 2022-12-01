package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Photos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import model.*;

/**
 * This is the class that lets the user controls the AlbumList scene.
 * @author Elizabeth Lam
 * @author Himani Patel
 */


public class UserController implements LogOutController{

	@FXML
	public Button openAlbum, addAlbum, deleteAlbum, renameAlbum, searchPhotos, logout;
	
	@FXML
	public ImageView image; 
	
	@FXML
	public Label albumCaption;

	
	/**
	 * the list of album names
	 */
	public static ArrayList<String> albumListNames = new ArrayList<String>();
	
	/**
	 * the administrator of the program
	 */
	public static Admin admin = Photos.driver;
	
	/**
	 * the current user
	 */
	public static User currentUser;
	
	//ListView<Album> listview = new ListView<Album>();
	//ObservableList<Album> obsList;
	
	@FXML
	private ListView<Album> listview;
	//= new ListView<String>(); 
	
	/**
	 * the list that will populate the listview
	 */
	private ObservableList<Album> obsList;
	
	
	/**
	 * initializes the album list screen using the parameters
	 * @param stage
	 * @param user
	 * @throws FileNotFoundException
	 * @throws URISyntaxException
	 */
	public void start(Stage stage, User user) throws FileNotFoundException, URISyntaxException {
		
	// TODO Auto-generated method stub
		//initialize list of albums
		currentUser = user;
		
		ArrayList<Album> albums;
			if (currentUser.getAlbumList() == null || currentUser.getAlbumList().size() == 0) {
				albums = new ArrayList<Album>();
			} else {
				albums = currentUser.getAlbumList();
			}
			
			
				//albums.add(new Album("hi"));
				//albums.add(new Album("hello"));
						//User.albums;
				//create an observable list from an arraylist
				obsList = FXCollections.observableArrayList(albums);
				
				listview.setItems(obsList);
				
				
				//select the first item

				
				//set listener
				listview.getSelectionModel().selectedIndexProperty().addListener(
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
				
				listview.getSelectionModel().select(0);
				
				//update();
				//currentUser = admin.getCurrent();
				//System.out.println(currentUser.username);
				stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, arg0 -> {
					try {
						closeWindowEvent(arg0);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});

		
	}

	/**
	 * saves all data if user closes the window
	 * @param event
	 * @throws IOException
	 */
	private void closeWindowEvent(WindowEvent event) throws IOException {
		Admin.save(Photos.driver);
	}
	
	
	/**
	 * shows the album thumbnail and its details
	 * @param stage
	 * @throws FileNotFoundException
	 */
	public void showItem(Stage stage) throws FileNotFoundException {
		Album currAlbum = listview.getSelectionModel().getSelectedItem();
		if (currAlbum == null) {
			image.setImage(null);
			albumCaption.setText("No image selected");
		} else {
			
			
			if (currAlbum.getFirstPhoto() != null) {
				albumCaption.setText(currAlbum.getDetails());
				File file = currAlbum.getFirstPhoto().getFile();
				InputStream stream = new FileInputStream(file.getAbsolutePath());
				Image image1 = new Image(stream);
				image.setImage(image1);
			} else {
				albumCaption.setText("No photos in album");
				image.setImage(null);
				
			}

			
			//image.setImage(currAlbum.getFirstPhoto().getImage()); 
	
		}	
		
		/*albumListNames.clear();
		for (int i = 0; i < currentUser.getAlbumList().size() ; i++) {
			albumListNames.add(currentUser.getAlbumList().get(i).getAlbumName());
		}
		listview.refresh();
		obsList = FXCollections.observableArrayList(albumListNames);
		listview.setItems(obsList);
		listview.refresh();*/
	}
	
	/**
	 * adds an album (once the add album button is pushed)
	 * @param event
	 * @throws IOException
	 */
	public void addAlbumAction(ActionEvent event) throws IOException {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Add Album");
		dialog.setHeaderText("Create a new album");
		dialog.setContentText("Please enter a name for the new album");
		
		Optional<String> name = dialog.showAndWait();
		String albumName = null;
		
		if (!name.isPresent()) {
			return;
		}
		
		// Added Code to check if Album already Exists
		if (name.isPresent()) {
			 albumName = name.get();
			if (currentUser.albumExists(albumName)) {
				
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setContentText("Album Name already exists.");
				alert.showAndWait();
				return;
			}
		}
		
	
		if (name.isPresent() && !currentUser.albumExists(albumName)) {
			Album newAlbum = new Album(name.get());
			
			//add newAlbum to albumList
			currentUser.addAlbum(newAlbum);
			
			obsList.add(newAlbum);
			User.save(currentUser);	

			listview.getSelectionModel().select(obsList.indexOf(newAlbum));
		}
	}
	
	/**
	 * deletes an album (once the delete album button is pushed)
	 * @param event
	 * @throws IOException
	 */
	public void deleteAlbumAction(ActionEvent event) throws IOException {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setContentText("Are you sure you want to delete this album?");
						
		Optional<ButtonType> answer = alert.showAndWait();
		if (answer.get() == ButtonType.OK) {
			Album currAlbum = listview.getSelectionModel().getSelectedItem();
			int index = obsList.indexOf(currAlbum);
			obsList.remove(index);
			currentUser.removeAlbum(index);
			User.save(currentUser);
			
			if (obsList.size() == 0) {
				albumCaption.setText(null);
				image.setImage(null);
			} else if (index == obsList.size()) {
				listview.getSelectionModel().select(index - 1);
				currAlbum = listview.getSelectionModel().getSelectedItem();
				if (currAlbum.getFirstPhoto() != null) {
					albumCaption.setText(currAlbum.getDetails());
					File file = currAlbum.getFirstPhoto().getFile();
					InputStream stream = new FileInputStream(file.getAbsolutePath());
					Image image1 = new Image(stream);
					image.setImage(image1);
				}
				//image.setImage(currAlbum.getFirstPhoto().getImage());
			} else {
				listview.getSelectionModel().select(index);
				currAlbum = listview.getSelectionModel().getSelectedItem();
				albumCaption.setText(currAlbum.getDetails());
				//image.setImage(currAlbum.getFirstPhoto().getImage());
				File file = currAlbum.getFirstPhoto().getFile();
				InputStream stream = new FileInputStream(file.getAbsolutePath());
				Image image1 = new Image(stream);
				image.setImage(image1);
			}
			
			
		}
		

	}
	
	/**
	 * renames an album (once the rename album button is pushed)
	 * @param event
	 * @throws IOException
	 */
	public void renameAlbumAction(ActionEvent event) throws IOException {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Rename Album");
		dialog.setHeaderText("Rename an existing album");
		dialog.setContentText("Please enter a new name for the album");
		
		Optional<String> newName = dialog.showAndWait();
		if (!newName.isPresent()) {
			return;
		}
		String albumName = newName.get();

		if (currentUser.albumExists(albumName)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setContentText("Album Name already exists.");
			alert.showAndWait();
			return;
		}
		if (newName.isPresent()) {
			Album currAlbum = listview.getSelectionModel().getSelectedItem();
			String name = newName.get();
			//System.out.println(name);
			
			currAlbum.renameAlbum(name);
			User.save(currentUser);	
			listview.refresh();
			listview.getSelectionModel().select(obsList.indexOf(currAlbum));
		}

	}
	
	/**
	 * opens album in the photolist screen (once the open album button is pushed)
	 * @param event
	 * @throws IOException
	 */
	public void openAlbumAction(ActionEvent event) throws IOException {
		Album currAlbum = listview.getSelectionModel().getSelectedItem();

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/PhotoList.fxml"));
		Parent root = loader.load();
		
		PhotoController c = loader.getController();
		
		Scene scene = new Scene(root);
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		
		c.start(stage, currAlbum, currentUser);
		
		stage.setScene(scene);
		stage.show();
		
		
	}
	
	/**
	 * searches all photos using input from the search photos screen (once the search photos button is pushed)
	 * @param event
	 * @throws IOException
	 */
	public void searchPhotosAction(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/SearchPhotos.fxml"));
		Parent root = loader.load();
		
		SearchPhotoController c = loader.getController();
		
		Scene scene = new Scene(root);
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		
		c.start(stage, currentUser);
		
		stage.setScene(scene);
		stage.show();
		
	}
	
	/**
	 * logs out of the program
	 * @param event
	 * @throws IOException
	 */
	public void logOut(ActionEvent event) throws IOException {
		LogOut(event);
	}
	
	}