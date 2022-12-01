package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import application.Photos;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 
 * This is the class controls the scene where user can add/delete tag values.
 * @author Himani Patel
 * @author Elizabeth Lam
 *
 */
public class PhotoDisplayController implements LogOutController {
	@FXML
	private Button addTag, back;
	@FXML
	private ImageView photo;
	@FXML
	private Label photoCaption;
	@FXML
	private ListView<Tag> tagList;
	@FXML
	private Button deleteTag;
	
	
	/**
	 * the photo to be displayed
	 */
	private Photo currPhoto;
	
	/**
	 * the observable tag list that will populate the listview
	 */
	private ObservableList<Tag> obsList;
	
	/**
	 * the current fx stage being shown
	 */
	private Stage currStage;
	
	/**
	 * the album the current photo is a part of
	 */
	private Album currAlbum;
	
	
	/**
	 * the admin 
	 */
	public static Admin admin = Photos.driver;
	
	/**
	 * the current user
	 */
	public static User currentUser;
	
	/**
	 * This method logs you out
	 * @param event
	 * @throws IOException
	 */
	public void logOut(ActionEvent event) throws IOException {
		LogOut(event);
	}
	/**
	 * This method allows the user to return to the Album Scene
	 * @param e
	 * @throws IOException
	 */
	public void backAction(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/PhotoList.fxml"));
		Parent root = loader.load();
		PhotoController c = loader.getController();
		Scene scene = new Scene(root);
		Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
		c.start(stage, currAlbum, currentUser);
		stage.setScene(scene);
		stage.show();
	
	}
	/**
	 * This method starts the PhotoDisplay Scene for the User.
	 * @param stage
	 * @param currPhoto
	 * @param currAlbum
	 * @throws FileNotFoundException 
	 */
	public void start (Stage stage, Photo currPhoto, Album currAlbum, User current) throws FileNotFoundException {
		currentUser = current;
		this.currAlbum = currAlbum;
		this.currPhoto = currPhoto;
		File file = currPhoto.getFile();
		InputStream stream = new FileInputStream(file.getAbsolutePath());
		Image image = new Image(stream);
		photo.setImage(image);
		//photo.setImage(currPhoto.getImage());
		photoCaption.setText(currPhoto.getCaption() + "\nDate: " + currPhoto.getFormattedDateTime());
		
		obsList = FXCollections.observableArrayList(currPhoto.getTagList());
		tagList.setItems(obsList);
		
		tagList.getSelectionModel().select(0);
		
		tagList.getSelectionModel().selectedIndexProperty().addListener(
				(obs, oldVal, newVal) -> 
				//showItem(mainStage)
				showItem(stage)
				);
	}
	/**
	 * This method allows the user to see the tag in the list view.
	 * @param stage
	 */
	public void showItem(Stage stage) {
		Tag currTag = tagList.getSelectionModel().getSelectedItem();
		
	}
	/**
	 * This method allows the user to add a tag to the photo selected prior entering scene.
	 * @param e
	 * @throws IOException 
	 */
	public void addTagAction(ActionEvent e) throws IOException {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Add Tag");
		dialog.setHeaderText("Add a new tag");
		dialog.setContentText("Please enter a tag type and value separated by a colon. "
				+ "\nex: location:france");
		
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			String[] tag = result.get().split(":");
			if (tag.length != 2) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Please enter tag field");
				alert.setContentText("The required fields have invalid input!");
				Optional<ButtonType> buttonClicked=alert.showAndWait();
				if (buttonClicked.get()==ButtonType.OK) {
					alert.close();
				}
			   else {
				   alert.close();
			   }
				return;
			}
			String newTagType = tag[0];
			String newTagVal = tag[1];
			
			Tag newTag = new Tag(newTagType, newTagVal);
			
			for (Tag t: currPhoto.getTagList()) {
				if (newTag.equals(t)) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Dialog");
					alert.setHeaderText("This tag already has been added.");
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
			
			obsList.add(newTag);
			currPhoto.getTagList().add(newTag);
			//Photo.save(currPhoto);
			tagList.getSelectionModel().select(obsList.indexOf(newTag));
			Admin.save(admin);
		}
	}
	/**
	 * This method allows the user to delete a tag from a photo.
	 * @param e
	 * @throws IOException 
	 */
	public void deleteTagAction(ActionEvent e) throws IOException {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setContentText("Are you sure you want to delete this tag?");
		
		Optional<ButtonType> answer = alert.showAndWait();
		if (answer.get() == ButtonType.OK) {
			Tag currTag = tagList.getSelectionModel().getSelectedItem();
			int index = obsList.indexOf(currTag);
			currPhoto.getTagList().remove(index);
			obsList.remove(index);
			//Photo.save(currPhoto);
			Admin.save(admin);
			
			if (index == obsList.size()) {
				tagList.getSelectionModel().select(index - 1);
			} else {
				tagList.getSelectionModel().select(index);
			}
			
			
		}
	}

}
