package controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

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
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.*;

/**
 * 
 *  This method implements the Search Photo scene.
 * @author Himani Patel
 * @author Elizabeth Lam
 *
 */

public class SearchPhotoController implements LogOutController {
	
	@FXML
	private TextField tagt1, tagt2, tagv1, tagv2, date1, date2;
	@FXML
	private Button sdate, s1tag, s2tag, s2tag1;

	public static Admin admin = Photos.driver;
	
	public static User currentUser;
	
	
	
	/**
	 * search for photos between two dates (when the search by date button is pushed)
	 * @param e
	 * @throws IOException
	 */
	public void searchByDate(ActionEvent e) throws IOException {
		
		String fromDate = date1.getText().trim();
		String toDate = date2.getText().trim();
		if (fromDate == null || toDate == null || 
				fromDate.isBlank() || toDate.isBlank()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Please enter fields of Date 1 and Date 2.");
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
		
		
		// date[0] - MM, date[1] - DD, date[2] - YYYY
		String[] date1Parts = fromDate.split("-");
		String[] date2Parts = toDate.split("-");
		
		if (date1Parts.length !=3 || date2Parts.length!=3 
				|| date1Parts[0].length() != 2 || date1Parts[1].length() != 2 || date1Parts[2].length() != 4
				|| date2Parts[0].length() != 2 || date2Parts[1].length() != 2 || date2Parts[2].length() != 4) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Please enter fields of Date 1 and Date 2.");
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
		
		for (int i = 0; i < date1Parts.length; i ++ ) {
			try {
				Integer.parseInt(date1Parts[i]);
			} catch (NumberFormatException nfe) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Please enter fields of Date 1 and Date 2.");
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
		}
		for (int i = 0; i < date2Parts.length; i ++ ) {
			try {
				Integer.parseInt(date2Parts[i]);
			} catch (NumberFormatException nfe) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Please enter fields of Date 1 and Date 2.");
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
		}
		
		
		LocalDateTime date1 = LocalDateTime.of(Integer.parseInt(date1Parts[2]), Integer.parseInt(date1Parts[0]), Integer.parseInt(date1Parts[1]), 0, 0);
		LocalDateTime date2 = LocalDateTime.of(Integer.parseInt(date2Parts[2]), Integer.parseInt(date2Parts[0]), Integer.parseInt(date2Parts[1]), 0, 0);
		
		ArrayList<Photo> photoList = currentUser.getDateRangePhotos(date1, date2);
		
		if (photoList == null || photoList.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("");
			alert.setContentText("No Photos found.");
			Optional<ButtonType> buttonClicked=alert.showAndWait();
			if (buttonClicked.get()==ButtonType.OK) {
				alert.close();
			}
		   else {
			   alert.close();
		   }
			return;
		}
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/SearchResults.fxml"));
		Parent root = loader.load();
		
		SearchResultsController c = loader.getController();
		
		Scene scene = new Scene(root);
		Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
		
		c.start(stage, photoList, currentUser);
		stage.setScene(scene);
		stage.show();
		
	}
	
	
	/**
	 * search for photos given a specific tag (when the search by one tag button is pushed)
	 * @param e
	 * @throws IOException
	 */
	public void searchByOneTag(ActionEvent e) throws IOException {
		
		String nameOfTag = tagt1.getText().trim();
		String valOfTag = tagv1.getText().trim();
		if (nameOfTag == null || valOfTag == null || nameOfTag.isBlank() || valOfTag.isBlank()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Please enter fields of Tag Type 1 and Tag Value 1.");
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
		Tag temp = new Tag(nameOfTag, valOfTag);
		ArrayList<Photo> photoList = currentUser.getOneTagPhotos(temp);
		//System.out.print("Retrieved Photo List");
		// No photos found
		if (photoList == null || photoList.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("");
			alert.setContentText("No Photos found.");
			Optional<ButtonType> buttonClicked=alert.showAndWait();
			if (buttonClicked.get()==ButtonType.OK) {
				alert.close();
			}
		   else {
			   alert.close();
		   }
			return;
		}
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/SearchResults.fxml"));
		Parent root = loader.load();
		
		SearchResultsController c = loader.getController();
		
		Scene scene = new Scene(root);
		Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
		
		c.start(stage, photoList, currentUser);
		stage.setScene(scene);
		stage.show();
		
	}
	
	
	/**
	 * seach photos with both tags (when the and button is pushed)
	 * @param e
	 * @throws IOException
	 */
	public void searchByAnd(ActionEvent e) throws IOException {
		String nameOfTag1 = tagt1.getText().trim();
		String valOfTag1 = tagv1.getText().trim();
		String nameOfTag2 = tagt2.getText().trim();
		String valOfTag2 = tagv2.getText().trim();
		if (nameOfTag1 == null ||nameOfTag2 == null || valOfTag1 == null ||valOfTag2 == null
				|| nameOfTag1.isBlank() || valOfTag1.isBlank() || nameOfTag2.isBlank() || valOfTag2.isBlank()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Please enter fields of all Tag Fields.");
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
	
		//Create The Two Tags Here and insert them into an ArrayList
		Tag tag1 = new Tag(nameOfTag1, valOfTag1);
		Tag tag2 = new Tag(nameOfTag2, valOfTag2);
		ArrayList<Tag> tagList = new ArrayList<>();
		tagList.add(tag1); 
		tagList.add(tag2); 
		
		// Call  getAndTagPhotos(ArrayList<Tag> tags)
		ArrayList<Photo> results = currentUser.getAndTagPhotos(tagList);
		//System.out.println(currentUser.username);
		// No photos found
		if (results == null || results.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("");
			alert.setContentText("No Photos found.");
			Optional<ButtonType> buttonClicked=alert.showAndWait();
			if (buttonClicked.get()==ButtonType.OK) {
				alert.close();
			}
		   else {
			   alert.close();
		   }
			return;
		}
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/SearchResults.fxml"));
		Parent root = loader.load();
		
		SearchResultsController c = loader.getController();
		
		Scene scene = new Scene(root);
		Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
		
		c.start(stage, results, currentUser);
		stage.setScene(scene);
		stage.show();
	}
	
	
	/**
	 * seach for photos with one of two tags (when the or button is pushed)
	 * @param e
	 * @throws IOException
	 */
	public void searchByOr(ActionEvent e) throws IOException {
		String nameOfTag1 = tagt1.getText().trim();
		String valOfTag1 = tagv1.getText().trim();
		String nameOfTag2 = tagt2.getText().trim();
		String valOfTag2 = tagv2.getText().trim();
		if (nameOfTag1 == null ||nameOfTag2 == null || valOfTag1 == null ||valOfTag2 == null
				|| nameOfTag1.isBlank() || valOfTag1.isBlank() || nameOfTag2.isBlank() || valOfTag2.isBlank()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Please enter fields of all Tag fields.");
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
		
		//Create The Two Tags Here and insert them into an ArrayList
				Tag tag1 = new Tag(nameOfTag1, valOfTag1);
				Tag tag2 = new Tag(nameOfTag2, valOfTag2);
				ArrayList<Tag> tagList = new ArrayList<>();
				tagList.add(tag1); 
				tagList.add(tag2); 
				
				// Call  getAndTagPhotos(ArrayList<Tag> tags)
				ArrayList<Photo> photoList = currentUser.getOrTagPhotos(tagList);
				// No photos found
				if (photoList == null || photoList.isEmpty()) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Dialog");
					alert.setHeaderText("");
					alert.setContentText("No Photos found.");
					Optional<ButtonType> buttonClicked=alert.showAndWait();
					if (buttonClicked.get()==ButtonType.OK) {
						alert.close();
					}
				   else {
					   alert.close();
				   }
					return;
				}
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/SearchResults.fxml"));
				Parent root = loader.load();
				
				SearchResultsController c = loader.getController();
				
				Scene scene = new Scene(root);
				Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
				
				c.start(stage, photoList, currentUser);
				stage.setScene(scene);
				stage.show();
		
			
	}
	/**
	 * This method allows the user to return to the Album List page.
	 * @param e
	 * @throws IOException
	 * @throws URISyntaxException 
	 */
	public void backAction(ActionEvent e) throws IOException, URISyntaxException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/AlbumList.fxml"));
		Parent root = loader.load();
		
		UserController userC = loader.getController();
		
		Scene scene = new Scene(root);
		Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
		
		userC.start(stage, currentUser);
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
	
	/** 
	 * initializes the search photos screen given the stage and the current user
	 * @param stage
	 * @param current
	 */
	public void start(Stage stage, User current) {
		// TODO Auto-generated method stub
		currentUser = current;
		
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
	 * saves all data when user closes window to terminate program
	 * @param event
	 * @throws IOException
	 */
	private void closeWindowEvent(WindowEvent event) throws IOException {
			Admin.save(Photos.driver);
	}

}
