package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


/**
 * This class is used to create Users and store methods that retrieve User Data.
 * @author Himani Patel
 * @author Elizabeth Lam
 *
 */
public class User implements Serializable {
	
	/**
	 * Serializable Interface used to store User Data
	 */
	private static final long serialVersionUID = 1L;
	public static final String storeDir = "dat";
	public static final String storeFile = "users.dat";
	
	/**
	 * Username of the current user
	 */
	private String username;
	/**
	 * List of albums to current user
	 */
	private ArrayList<Album> albums;
	/**
	 * Current Album in use
	 */
	private Album currentAlbum; //used for opening albums
	/**
	 * Constructor
	 * @param username
	 */
	public User(String username) {
		this.username = username;
		albums = new ArrayList<Album>();
	}
	/**
	 * gets username of the user
	 * @return username
	 */
	public String getUsername(){		
		return this.username;
	}
	/**
	 * get album
	 * @param index
	 * @return get album
	 */
	public Album getAlbum(int index) {
		return albums.get(index);
	}
	
	/**
	 * checks if an album name already exists
	 * @param name
	 * @return
	 */
	public boolean albumExists(String name){
		for (Album a: albums) {
			if (a.getAlbumName().equals(name.toString())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * gets the list of albums
	 * @return albums
	 */
	public ArrayList<Album> getAlbumList(){
		return albums;
	}
	/**
	 * Adds an album to the user's album list
	 * @param album
	 */
	public void addAlbum(Album album) {
		albums.add(album);
	}
	/**
	 * removes an album
	 * @param index
	 */
	public void removeAlbum(int index) {
		albums.remove(index);
	}
	
	/**
	 * With the use of one tag, this method returns a list of Photos that contain Tag t.
	 * @param t
	 * @return
	 */
	public ArrayList<Photo> getOneTagPhotos(Tag t){
		ArrayList<Photo> photos = new ArrayList<Photo>();
		HashSet<Photo> check = new HashSet<Photo>();
		for(Album album : albums) {
			for(Photo photo : album.getAllPhotos()) {
				if(photo.tagExists(t.getName(), t.getValue())) {
					check.add(photo);
				}
			}
			
		}
		photos.addAll(check);
		return photos;
	}
	/**
	 * This method searched and returns the list of photos consisting the the conjunction of two tags.
	 * @param tags
	 * @return
	 */
	public ArrayList<Photo> getAndTagPhotos(ArrayList<Tag> tags){
		ArrayList<Photo> photos = new ArrayList<Photo>();
		HashSet<Photo> check = new HashSet<Photo>(); //for dupes
	
			for(Album album : albums) {
				for(Photo photo : album.getAllPhotos()) {
					if(photo.getTagList().containsAll(tags)) {
						check.add(photo);
					}
				}
				
		}
		photos.addAll(check);
		return photos;
	}	
	/**
	 * This method searched and returns the list of photos consisting the the disjunction of two tags.
	 * @param tags
	 * @return
	 */
	public ArrayList<Photo> getOrTagPhotos(ArrayList<Tag> tags){
		ArrayList<Photo> photos = new ArrayList<Photo>();
		HashSet<Photo> check = new HashSet<Photo>();
		for(Tag tag : tags) {
			for(Album album : albums) {
				for(Photo photo : album.getAllPhotos()) {
					if(photo.tagExists(tag.getName(), tag.getValue())) {
						check.add(photo);
					}
				}
				
			}
		}
		photos.addAll(check);
		return photos;
	}
	/**
	 * method returns a list of photos that between the range of one date to another date
	 * @param d1
	 * @param d2
	 * @return
	 */
	public ArrayList<Photo> getDateRangePhotos(LocalDateTime d1, LocalDateTime d2){
		ArrayList<Photo> results = new ArrayList<>();
		HashSet<Photo> check = new HashSet<Photo>();

		for(Album album : albums) {
			for(Photo photo : album.getAllPhotos()) {
				
				if (photo.getDateTime().compareTo(d1) > 0 && photo.getDateTime().compareTo(d2) < 0) {
					check.add(photo);
				}
				
			}
		}
		
		results.addAll(check);
		
		return results;
	}
	/**
	 * loads data from the user
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
	/**
	 * saves User data
	 * @param pdApp
	 * @throws IOException
	 */
	public static void save(User pdApp) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(pdApp);
		oos.close();
	}
}
