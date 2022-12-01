package controller;


import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import application.Photos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Admin;
import model.Album;
import model.Photo;
import model.User;
/**
 * This class is used to control the Slide show scene which allows the user to view their photos in an album
 * @author Himani Patel
 * @author Elizabeth Lam
 *
 */

public class SlideshowController implements LogOutController {
	@FXML
	private Button back;
	@FXML
	private Button forward, logout, backStage;
	@FXML
	private ImageView currImage;
	
	/**
	 * the photos that will be shown in the slideshow 
	 */
	private ArrayList<Photo> photos;
	/**
	 * the index of the current photo being shown
	 */
	private int currIndex;
	
	/**
	 * the previous stage
	 */
	private Stage prevStage;
	/**
	 * the current album
	 */
	private Album currAlbum;
	/**
	 * the current user
	 */
	private User currentUser;

	/**
	 * This method starts the slide show scene showing the first photo
	 * @param stage
	 * @param photos
	 * @throws FileNotFoundException 
	 */
	public void start (Stage stage, Stage prevStage, Album currAlbum, User currentUser) throws FileNotFoundException {

		this.photos = currAlbum.getAllPhotos();
		this.currAlbum = currAlbum;
		this.currentUser = currentUser;
		this.prevStage = prevStage;
		currIndex = 0;
		File file = photos.get(0).getFile();
		InputStream stream = new FileInputStream(file.getAbsolutePath());
		Image image1 = new Image(stream);
		currImage.setImage(image1);
		//currImage.setImage( photos.get(0).getImage());
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
	 * This method allows the user to return the the previous image seen.
	 * @param e
	 * @throws FileNotFoundException 
	 */
	public void backAction(ActionEvent e) throws FileNotFoundException {
		if (currIndex == 0) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("WARNING");
			alert.setHeaderText("Cannot go back");
			alert.showAndWait();
		} else {
			currIndex -= 1;
			File file = photos.get(currIndex).getFile();
			InputStream stream = new FileInputStream(file.getAbsolutePath());
			Image image1 = new Image(stream);
			currImage.setImage(image1);
			//currImage.setImage( photos.get(currIndex).getImage());
		}
	}
	/**
	 * This method allows the user to view the next photo.
	 * @param e
	 * @throws FileNotFoundException 
	 */
	public void forwardAction(ActionEvent e) throws FileNotFoundException {
		if (currIndex == (photos.size() - 1)) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("WARNING");
			alert.setHeaderText("Cannot go forward");
			alert.showAndWait();
		} else {
			currIndex += 1;
			File file = photos.get(currIndex).getFile();
			InputStream stream = new FileInputStream(file.getAbsolutePath());
			Image image1 = new Image(stream);
			currImage.setImage(image1);
			//currImage.setImage( photos.get(currIndex).getImage());
		}
	}
	
	/**
	 * allows the user to go back to the previous stage (when the back option is pushed)
	 * @param e
	 * @throws IOException
	 */
	public void backStageAction(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/PhotoList.fxml"));
		Parent root = loader.load();
		
		PhotoController userC = loader.getController();
		
		Scene scene = new Scene(root);
		Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
		
		userC.start(stage, currAlbum, currentUser);
		stage.setScene(scene);
		stage.show();
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
