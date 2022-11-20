package model;

import java.util.*;


/**
 * 
 * 
 * @author Himani Patel
 * @author Elizabeth Lam
 *
 */
public class User {
	
	/**
	 * Username of the current user
	 */
	public String username;
	/**
	 * List of albums to current user
	 */
	public ArrayList<Album> albums;
	/**
	 * Current Album in use
	 */
	public Album currentAlbum; //used for opening albums
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
	public boolean albumExists(Album album){
		for (Album a: albums) {
			if (a.getAlbumName().equals(album.AlbumName)){
				return true;
			}
		}
		return false;
	}
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
	


}
