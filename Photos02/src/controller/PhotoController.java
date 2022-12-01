package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;

import application.Photos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import model.Admin;
import model.Album;
import model.Photo;
import model.User;
/**
 * This class is used to control the Photo List scene allowing the user to implement many methods with their Album.
 * @author Himani Patel
 * @author elizabeth lam
 *
 */
public class PhotoController implements LogOutController{
	@FXML
	private ListView<Photo> photoList;
	@FXML
	private Button displayPhoto, back;
	@FXML
	private Button addPhoto;
	@FXML
	private Button deletePhoto;
	@FXML
	private Button renamePhoto;
	@FXML
	private Button copyPhoto;
	@FXML
	private Button logout;
	@FXML
	private Button movePhoto;
	@FXML
	private Button slideshow;
	
	private ObservableList<Photo> obsList;
	
	private Stage currStage;
	
	private Album album;
	
	private ArrayList<Photo> photos;
	
	public static Admin admin = Photos.driver;
	
	public static User currentUser;
	
	/**
	 * This method starts the Photo scene and creates the list view of the album names.
	 * @param stage
	 * @param currAlbum
	 * @throws FileNotFoundException 
	 */
	public void start(Stage stage, Album currAlbum, User current) throws FileNotFoundException {
		currentUser = current;
		currStage = stage;
		album = currAlbum;
			if (currAlbum.getAllPhotos().size() == 0) {
				photos = new ArrayList<>();
			} else {
				photos = currAlbum.getAllPhotos();
			}
		//photos.add(new Photo(photos.size()));
		//photos.add(new Photo(photos.size()));

		obsList = FXCollections.observableArrayList(photos);
		
		photoList.setItems(obsList);
		
		photoList.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>() {
			@Override
			public Cell call(ListView<Photo> listView) {
				return new Cell();
			}
		}
				);
		
		//select the first item
	

		
		//set listener
		photoList.getSelectionModel().selectedIndexProperty().addListener(
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
		
		photoList.getSelectionModel().select(0);
		
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
	 * This method shows the image of the photo selected.
	 * @param stage
	 * @throws FileNotFoundException 
	 */
	public void showItem(Stage stage) throws FileNotFoundException {
		Photo currPhoto = photoList.getSelectionModel().getSelectedItem();
		/*if (currPhoto == null) {
			photo.setImage(null);
			photoCaption.setText(null);
		} else {
			photoCaption.setText(currPhoto.getCaption());
			File file = currPhoto.getFile();
			InputStream stream = new FileInputStream(file.getAbsolutePath());
			Image image = new Image(stream);
			photo.setImage(image);
		}*/
	}
	public void backAction(ActionEvent e) throws IOException, URISyntaxException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/AlbumList.fxml"));
		//root = FXMLLoader.load(getClass().getResource("/view/AlbumList.fxml"));
		Parent root = loader.load();
		UserController userC = loader.getController();
		
		Scene scene = new Scene(root);
		Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
		
		userC.start(stage, currentUser);
		
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * This method allows the user to enter the display photo scene to add/delete tags.
	 * @param e
	 * @throws IOException
	 */
	public void displayPhotoAction(ActionEvent e) throws IOException {
		Photo currPhoto = photoList.getSelectionModel().getSelectedItem();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/PhotoDisplay.fxml"));
		Parent root = loader.load();
		
		PhotoDisplayController c = loader.getController();
		
		Scene scene = new Scene(root);
		
		Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
		
		c.start(stage, currPhoto, this.album, currentUser);
		
		stage.setScene(scene);
		stage.show();
		
		
	}
	/**
	 * This method allows the user to add photos to their album
	 * @param e
	 * @throws IOException 
	 */
	public void addPhotoAction(ActionEvent e) throws IOException {
		FileChooser fc = new FileChooser();
		FileChooser.ExtensionFilter fileExtensions = new FileChooser.ExtensionFilter(
			    "Photo types", "*.jpeg", "*.png", "*.gif", "*.jpg", "*.bmp");

		fc.getExtensionFilters().add(fileExtensions);
		fc.setTitle("Add New Photo");
		File file = fc.showOpenDialog(currStage);
		
		if (file != null) {
			//InputStream stream = new FileInputStream(file.getAbsolutePath());
			//Image image = new Image(stream);
			String filepath = file.getAbsolutePath();
			boolean ifCopy = false;
			Photo newPhoto = new Photo(album.getAllPhotos().size());
			
			for(Album album : currentUser.getAlbumList()) {
				for(Photo photo : album.getAllPhotos()) {
					if(album.getAlbumName().equals(this.album.getAlbumName()) && photo.getFilePath().equals(filepath)) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("ERROR");
						alert.setHeaderText("You cannot have duplicate photos in an album.");
						alert.showAndWait();
						return;
					} else if (photo.getFilePath().equals(filepath)) {
						ifCopy = true;
						newPhoto = photo;
						break;
					}
				}
			}
			
			if(!ifCopy) {		
				newPhoto.setFilePath(filepath);
				newPhoto.setFile(file);
				//newPhoto.setImage(image);
				
				TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle("Add Photo");
				dialog.setHeaderText("Add a new photo to the album");
				dialog.setContentText("Please enter a caption for the new photo");
				
				Optional<String> name = dialog.showAndWait();
				if (name.isPresent()) {
					newPhoto.setCaption(name.get());
				}
			}

			//photos.add(newPhoto);
			obsList.add(newPhoto);
			album.addPhoto(newPhoto);
			photoList.getSelectionModel().select(obsList.indexOf(newPhoto));
			//Photo.save(newPhoto);
			Album.save(album);
		}	
	}
	/**
	 * This method allows the user to delete a photo from their album.
	 * @param e
	 * @throws IOException 
	 */
	public void deletePhotoAction(ActionEvent e) throws IOException {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setContentText("Are you sure you want to delete this photo?");
		
		Optional<ButtonType> answer = alert.showAndWait();
		if (answer.get() == ButtonType.OK) {
			Photo currPhoto = photoList.getSelectionModel().getSelectedItem();
			int index = obsList.indexOf(currPhoto);
			//photos.remove(index);
			album.deletePhoto(index);
			obsList.remove(index);
			
			/*if (obsList.size() == 0) {
				photoCaption.setText(null);
				//photo.setImage(null);
				photo.setImage(null);
				
			} else */
			if (index == obsList.size()) {
				photoList.getSelectionModel().select(index - 1);
				currPhoto = photoList.getSelectionModel().getSelectedItem();
				/*photoCaption.setText(currPhoto.getCaption());
				//photo.setImage(currPhoto.getImage());
				File file = currPhoto.getFile();
				InputStream stream = new FileInputStream(file.getAbsolutePath());
				Image image = new Image(stream);
				photo.setImage(image);*/
			} else {
				photoList.getSelectionModel().select(index);
				currPhoto = photoList.getSelectionModel().getSelectedItem();
				/*photoCaption.setText(currPhoto.getCaption());
				File file = currPhoto.getFile();
				InputStream stream = new FileInputStream(file.getAbsolutePath());
				Image image = new Image(stream);
				photo.setImage(image);*/
				//photo.setImage(currPhoto.getImage());
			}
			//Photo.save(currPhoto);
			Album.save(album);
		}
	}
	
	/**
	 * This method allows the user to add a caption or re-add a caption to a photo.
	 * @param e
	 * @throws IOException 
	 */
	public void recaptionPhotoAction(ActionEvent e) throws IOException {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Recaption Photo");
		dialog.setHeaderText("Recaption an existing photo");
		dialog.setContentText("Please enter a new caption for the photo");
		
		Optional<String> newName = dialog.showAndWait();
		if (newName.isPresent()) {
			Photo currPhoto = photoList.getSelectionModel().getSelectedItem();
			currPhoto.setCaption(newName.get());

			
			photoList.getSelectionModel().select(obsList.indexOf(currPhoto));
			//photoCaption.setText(currPhoto.getCaption());
			currPhoto.updateDateTime();
			//Photo.save(currPhoto);
			Album.save(album);
		}
		photoList.refresh();
	}
	
	/**
	 * This method allows the user to copy a photo from one album to another album.
	 * @param e
	 * @throws IOException 
	 */
	public void copyPhotoAction(ActionEvent e) throws IOException {
		Photo currPhoto = photoList.getSelectionModel().getSelectedItem();
		ChoiceDialog<Album> dialog = new ChoiceDialog<Album>(currentUser.getAlbumList().get(0), currentUser.getAlbumList());
		dialog.setHeaderText("Copy Photo");
		dialog.setContentText("Please select the album you would like to copy the photo to");
		Optional<Album> result = dialog.showAndWait();
		
		if (!result.isPresent()) {
			return;
		}
		
		Album copyTo = dialog.getSelectedItem();
		
		if (copyTo.getAlbumName() == album.getAlbumName()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("You are not allowed duplicate photos in an album.");
			alert.showAndWait();
			return;
		}
		for(Photo photo : copyTo.getAllPhotos()) {
			if (photo.getFilePath().equals(currPhoto.getFilePath())) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setHeaderText("You cannot have duplicate photos in an album.");
				alert.showAndWait();
				return;
			}
		}
		
		if (copyTo != null) {
			copyTo.addPhoto(currPhoto);
			//Photo.save(currPhoto);
			Album.save(album);
		} 
		
	}
	
	/**
	 * This method allows the user to move a photo to an existing album.
	 * @param e
	 * @throws IOException 
	 */
	public void movePhotoAction(ActionEvent e) throws IOException {
		Photo currPhoto = photoList.getSelectionModel().getSelectedItem();
		ChoiceDialog<Album> dialog = new ChoiceDialog<Album>(currentUser.getAlbumList().get(0), currentUser.getAlbumList());
		dialog.setHeaderText("Move Photo");
		dialog.setContentText("Please select the album you would like to move the photo to");
		Optional<Album> result = dialog.showAndWait();
		
		if (!result.isPresent()) {
			return;
		}
		
		Album moveTo = dialog.getSelectedItem();
		
		if (moveTo.getAlbumName() == album.getAlbumName()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("This is the album you are moving the photo to");
			alert.showAndWait();
			return;
		}
		for(Photo photo : moveTo.getAllPhotos()) {
			if (photo.getFilePath().equals(currPhoto.getFilePath())) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setHeaderText("You cannot have duplicate photos in an album.");
				alert.showAndWait();
				return;
			}
		}
		
		if (moveTo != null || moveTo != album) {
			moveTo.addPhoto(currPhoto);
			
			int index = album.getAllPhotos().indexOf(currPhoto);
			photos.remove(index);
			obsList.remove(index);
			
			/*if (obsList.size() == 0) {
				photoCaption.setText(null);
				photo.setImage(null);
			} else */
			if (index == obsList.size()) {
				photoList.getSelectionModel().select(index - 1);
				currPhoto = photoList.getSelectionModel().getSelectedItem();
				/*photoCaption.setText(currPhoto.getCaption());
				File file = currPhoto.getFile();
				InputStream stream = new FileInputStream(file.getAbsolutePath());
				Image image = new Image(stream);
				photo.setImage(image);*/
				//photo.setImage(currPhoto.getImage());
			} else {
				photoList.getSelectionModel().select(index);
				currPhoto = photoList.getSelectionModel().getSelectedItem();
				/*photoCaption.setText(currPhoto.getCaption());
				File file = currPhoto.getFile();
				InputStream stream = new FileInputStream(file.getAbsolutePath());
				Image image = new Image(stream);
				photo.setImage(image);*/
				//photo.setImage(currPhoto.getImage());
			}
			
		}
		//Photo.save(currPhoto);
		Album.save(album);
	}
	/**
	 * This method allows the user to create a slide show view.
	 * @param e
	 * @throws IOException
	 */
	public void slideshowAction(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Slideshow.fxml"));
		Parent root = loader.load();
		
		SlideshowController c = loader.getController();
		
		Scene scene = new Scene (root);
		Stage newStage = (Stage)((Node) e.getSource()).getScene().getWindow();
		
		c.start(newStage, currStage, album, currentUser);
		
		newStage.setScene(scene);
		newStage.show();
		
		
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
