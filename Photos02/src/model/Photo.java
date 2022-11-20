package model;
import java.io.File;
import java.util.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;

/**
 * 
 * 
 * @author Himani Patel
 * @author Elizabeth Lam
 *
 */
public class Photo {
	
	/**
	 * name of Photo
	 */
	public String PhotoName;
	/**
	 * File of photo
	 */
	public File photo;
	/**
	 * ArrayList of Tags
	 */
	public ArrayList<Tag> TagList;
	/**
	 * photo's caption
	 */
	public String caption;
	/**
	 * the calendar in the photo
	 */
	public Calendar cal;
	/**
	 * current date of photo
	 */
	public Date date;
	/**
	 * Photo constructor
	 * @param photo
	 * @param PhotoName
	 */
	public Photo(File photo, String PhotoName) {
		if (photo != null) {
			this.photo = new File(PhotoName);
		} else {
			this.photo = photo;
		}
		this.PhotoName = PhotoName;
		this.TagList = new ArrayList<Tag>();
		this.caption = null;
		cal = new GregorianCalendar();
		cal.set(Calendar.MILLISECOND, 0);
		this.date = cal.getTime();
	}
	/**
	 * This method adds/sets a new caption to the photo
	 * @param caption
	 */
	public void addCaption(String caption) {
		this.caption = caption;
	}
	/**
	 * Add a new tag to the photo
	 * @param name of tag
	 * @param value of tag
	 */
	public void addTag(String name, String value) {
		TagList.add(new Tag(name,value));
	}
	public void removeTag(String name, String value){
		for (int i = 0; i < TagList.size(); i++) {
			if (TagList.get(i).getName().toLowerCase().equals(name.toLowerCase()) && TagList.get(i).getValue().toLowerCase().equals(value.toLowerCase()) ) {
				TagList.remove(i);
			}
		}
		
	}
	/**
	 * Method checks if tag exists
	 * @param name
	 * @param value
	 * @return
	 */
	public boolean tagExists(String name, String value) {
		
		for (int i = 0; i < TagList.size(); i++) {
			if (TagList.get(i).getName().toLowerCase().equals(name.toLowerCase()) && TagList.get(i).getValue().toLowerCase().equals(value.toLowerCase()) ) {
				return true;
			}
		}
		return false;
	}
	/**
	 * returns tag list
	 * @return
	 */
	public ArrayList<Tag> getTagList(){
		return TagList;
	}
	/**
	 * returns Date of Photo
	 * @return
	 */
	public Date getData() {
		return date;
	}
	
	
	

}
