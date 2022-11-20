package model;
import java.util.*;
/**
 * 
 * 
 * @author Himani Patel
 * @author Elizabeth Lam
 *
 */
public class Album {
	
	/**
	 * is the name of album
	 */
	public String AlbumName;
	
	/**
	 * is the list of photos in a album
	 */
	public ArrayList<Photo> PhotoList;
	
	/**
	 * Current photo in the album
	 */
	public Photo currentPhoto;
	
	/**
	 * Constructor for Album
	 * @param albumName 
	 */
	public Album(String AlbumName) {
		this.AlbumName = AlbumName; 
		PhotoList = new ArrayList<Photo>();
	}
	/**
	 * returns arraylist of Photos
	 * @return
	 */
	public ArrayList<Photo> getAllPhotos(){
		return PhotoList;
	}
	/**
	 * returns current photo
	 * @return
	 */
	public Photo getCurrentPhoto() {
		return currentPhoto;
	}
	/**
	 * sets current photo as p
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
		PhotoList.add(p);
	}
	/**
	 * searches for a Photo in the album by given Name
	 * @param PhotoName
	 * @return Photo
	 */
	public Photo searchPhoto(String PhotoName) {
		for (Photo p: PhotoList) {
			if (p.PhotoName.equals(PhotoName)) {
				return p;
			}
		}
		return null;
	}
	/**
	 * Deletes a photo to the Album given the name of Photo
	 * @param PhotoName
	 */
	public void deletePhoto(String PhotoName) {
		for (Photo p: PhotoList) {
			if (p.PhotoName.equals(PhotoName)) {
				int i = PhotoList.indexOf(p);
				PhotoList.remove(i);
			}
		}
	}
	/**
	 * Deletes a photo of Album by index
	 * @param index
	 */
	public void deletePhoto(int index) {
		PhotoList.remove(index);
	}
	@Override
	public String toString() {
		return getAlbumName();
	}
	

}
