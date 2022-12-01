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
import java.util.*;
/**
 * 
 * This class is used to store methods to manipulate albums that hold a list of photos.
 * @author Himani Patel
 * @author Elizabeth Lam
 *
 */
public class Album implements Serializable {
	
	/**
	 * Serializable Interface used to store User Data
	 */
	private static final long serialVersionUID = 1L;
	public static final String storeDir = "dat";
	public static final String storeFile = "users.dat";
	
	/**
	 * is the name of album
	 */
	private String AlbumName;
	
	/**
	 * is the list of photos in a album
	 */
	private ArrayList<Photo> photoList;
	
	/**
	 * Current photo in the album
	 */
	private Photo currentPhoto;
	
	/**
	 * Earliest photo dated in album
	 */
	private String earliestDate;
	/**
	 *  Latest photo dated in album
	 */
	private String latestDate;
	/**
	 * Constructor for Album
	 * @param albumName 
	 */
	public Album(String AlbumName) {
		this.AlbumName = AlbumName; 
		photoList = new ArrayList<Photo>();
	}
	/**
	 * returns arraylist of Photos
	 * @return
	 */
	public ArrayList<Photo> getAllPhotos(){
		return photoList;
	}
	/**
	 * returns the first photo of Album
	 * @return
	 */
	public Photo getFirstPhoto() {
		if (photoList.size() == 0) {
			return null;
		}
		return photoList.get(0);
	}
	/**
	 * returns current photo
	 * @return
	 */
	public Photo getCurrentPhoto() {
		return currentPhoto;
	}
	/**
	 * sets p as current photo
	 * @param p
	 */
	public void setCurrentPhoto(Photo p) {
		this.currentPhoto = p;
	}
	/**
	 * Renames the album to the given name
	 * @param name
	 */
	public void renameAlbum(String newName) {
		this.AlbumName = newName;
	}
	/**
	 * Returns the album name
	 * @return
	 */
	public String getAlbumName() {
		return this.AlbumName;
	}

	/**
	 * Adds photo to album
	 * @param p
	 */
	public void addPhoto(Photo p) {
		photoList.add(p);
	}
	/*
	public Photo searchPhoto(String PhotoName) {
		for (Photo p: photoList) {
			if (p.photoName.equals(PhotoName)) {
				return p;
			}
		}
		return null;
	}
	*/
	/**
	 * Deletes a photo to the Album given the name of Photo
	 * @param PhotoName
	 */
	public void deletePhoto(String PhotoName) {
		for (Photo p: photoList) {
			if (p.getName().equals(PhotoName)) {
				int i = photoList.indexOf(p);
				photoList.remove(i);
			}
		}
	}
	/**
	 * Deletes a photo of Album by index
	 * @param index
	 */
	public void deletePhoto(int index) {
		photoList.remove(index);
	}
	/**
	 * Returns String of Date Range
	 * @return
	 */
	public String getDateRange() {
		return getEarliestDate() + " - \n\t" + getLatestDate();
	}
	/**
	 * Returns String Name of Album Name
	 */
	@Override
	public String toString() {
		return AlbumName;
	}
	/**
	 * Returns details of Album Name
	 * @return
	 */
	public String getDetails() {
		return ("Album Name: " + AlbumName + "\nNumber of Photos: " + photoList.size() + "\nDate Range: " + getDateRange());

	}
	
	/**
	 * get the date of the earliest photo in the album
	 * @return the earliest date
	 */
	public String getEarliestDate() {
		LocalDateTime earliestDate = photoList.get(0).getDateTime();
		for (int i = 1; i < photoList.size(); i++) {
			if (photoList.get(i).getDateTime().compareTo(earliestDate) < 0) {
				earliestDate = photoList.get(i).getDateTime();
			}
		}
		return earliestDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
	}
	
	/**
	 * get the date of the latest photo in the album
	 * @return the latest date
	 */
	public String getLatestDate() {
		LocalDateTime latestDate = photoList.get(0).getDateTime();
		for (int i = 1; i < photoList.size(); i++) {
			if (photoList.get(i).getDateTime().compareTo(latestDate) > 0) {
				latestDate = photoList.get(i).getDateTime();
			}
		}
		return latestDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
	}
	/**
	 * Used to store data of the Album class
	 * @param Adapp
	 * @throws IOException
	 */
	public static void save(Album Albumapp) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(Albumapp);
		oos.close();
	}

	public static User load() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		User userList = (User) ois.readObject();
		ois.close();
		return userList;
	}
	

}
