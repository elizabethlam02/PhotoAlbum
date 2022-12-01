package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.scene.image.Image;

/**
 * This class implements a photo and stores methods used to manipulate a photo in the photo library.
 * @author Himani Patel 
 * @author Elizabeth Lam
 *
 */
public class Photo implements Serializable {
	
	/**
	 * Serializable Interface used to store User Data
	 */
	private static final long serialVersionUID = 1L;
	public static final String storeDir = "dat";
	public static final String storeFile = "users.dat";
	/**
	 * Photo Name
	 */
	private String photoName;
	/**
	 * Image of the Photo stored
	 */
	//Image image;
	
	private File file;
	/**
	 * Photo Caption
	 */
	private String photoCaption;
	/**
	 * File path of photo
	 */
	private String filepath;
	/**
	 * Photo's Tags
	 */
	private ArrayList<Tag> tags = new ArrayList<Tag>();
	/**
	 * Photo's Date
	 */
	private LocalDateTime dt = LocalDateTime.now();
	
	/**
	 * Photo Constructors
	 * @param num
	 */
	public Photo (int num) {
		photoName = "IM_" + num;
	}
	
	public Photo(String name) {
		photoName = name;
	}
	
	/*
	public void setImage(Image i) {
		image = i;
	}
	public  Image getImage() {
		return image;
	}
	*/
	/**
	 * sets the File to the Photo class
	 * @param i
	 */
	public void setFile(File i) {
		file = i;
	}
	/**
	 * retrieves image file
	 * @return
	 */
	public  File getFile() {
		return file;
	}
	/**
	 * saves file path for serializable
	 * @param fp
	 */
	public void setFilePath(String fp) {
		this.filepath = fp;
	}
	/**
	 * returns string of the file path
	 * @return
	 */
	public String getFilePath() {
		return filepath;
	}
	/**
	 * returns the name of the photo
	 * @return
	 */
	public String getName() {
		return photoName;
	}
	/**
	 * sets the caption of the photo object
	 * @param cap
	 */
	public void setCaption(String cap) {
		photoCaption = cap;
	}
	/**
	 * returns the caption of the photo
	 * @return
	 */
	public String getCaption() {
		return photoCaption;
	}
	/**
	 * returns photo's tag list
	 * @return
	 */
	public ArrayList<Tag> getTagList() {
		return tags;
	}
	/**
	 * updates the date time of the photo
	 */
	public void updateDateTime() {
		dt = LocalDateTime.now();
	}
	/**
	 * returns the date time of the photo
	 * @return
	 */
	public LocalDateTime getDateTime() {
		return dt;
	}
	/**
	 * returns the formatted dated time as a string
	 * @return
	 */
	public String getFormattedDateTime() {
		return dt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy \n HH:mm:ss"));
		
	}
	/**
	 * returns true if the tag exits
	 * @param name
	 * @param value
	 * @return
	 */
	public boolean tagExists(String name, String value) {
		for(int i = 0; i < tags.size(); i++) {
			Tag curr = tags.get(i);
			if(curr.getName().toLowerCase().equals(name.toLowerCase()) && curr.getValue().toLowerCase().equals(value.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	/**
	 * returns the photo obj as a string
	 */
	public String toString() {
		return photoName;
	}
	/**
	 * saves the data of the photo
	 * @param photoApp
	 * @throws IOException
	 */
	public static void save(Photo photoApp) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(photoApp);
		oos.close();
	}
	/**
	 * loads user data of photo
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static User load() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		User userList = (User) ois.readObject();
		ois.close();
		return userList;
	}


}
