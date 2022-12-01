package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import model.Photo;

/**
 * creates the listcells of the listview which house the photos and their labels
 * @author Himani Patel
 * @author Elizabeth Lam
 */

public class Cell extends ListCell<Photo>{
	
	/**
	 * the box that populates the listview with imageview and label
	 */
	HBox root;
	
	/**
	 * the thumbnail photo
	 */
	ImageView image;
	
	/**
	 * the photo caption
	 */
	Label imCap;
	
	
	/**
	 * initializes the cell with its components 
	 */
	public Cell() {
		super();
		root = new HBox();
		image = new ImageView();
		imCap = new Label();
		
		image.setFitHeight(100);
		image.setFitWidth(150);

		root.getChildren().addAll(image, imCap);
		HBox.setHgrow(imCap, Priority.ALWAYS);
	}
	
	
	
	/**
	 * updates each cell with the correct photo object
	 */
	@Override
	public void updateItem(Photo photo, boolean empty) {
		super.updateItem(photo, empty);
		setText(null);
		setGraphic(null);
		
		if (photo != null && !empty) {
			imCap.setText(photo.getCaption());
			File file = photo.getFile();
			InputStream stream = null;
			try {
				stream = new FileInputStream(file.getAbsolutePath());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Image image1 = new Image(stream); 
			image.setImage(image1);
			setGraphic(root);
		}
	}
	
}
