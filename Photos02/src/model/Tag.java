package model;

import java.io.Serializable;

/**
 * This class is used to store methods that can be used to manipulate tags.
 * @author Himani Patel
 * @author Elizabeth Lam
 *
 */

public class Tag implements Serializable {
	
	/**
	 * Serializable Interface used to store User Data
	 */
	private static final long serialVersionUID = 1L;
	public static final String storeDir = "dat";
	public static final String storeFile = "users.dat";
	/**
	 * Name of the tag
	 */
	private String name;
	/**
	 * Value of the Tag
	 */
	private String value;
	
	
	/**
	 * Tag Constructor
	 * @param name String
	 * @param valiue String
	 */
	public Tag(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	/**
	 * retrieves the name of tag
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * retrieves the value of the tag
	 * @return
	 */
	public String getValue() {
		return this.value;
	}
	/**
	 *sets name to the tag
	 * @param name
	 */
	public void setName(String name){
		this.name = name;
	}
	/**
	 * sets a new value to tag
	 * @param value
	 */
	public void setValue(String value){
		this.value = value;
	}
	
	/**
	 * equals method of tag
	 */
	@Override
	public boolean equals(Object o) {
		if(o == null) {
			if(!( o instanceof Tag)){
				return false;
			}
		}
		
		Tag t = (Tag) o;
		return t.name.equals(this.name) && t.value.equals(this.value);
	}
	
	/**
	 * toString of Tag
	 */
	@Override
	public String toString() {
		return name + " : " + value; 
	} 
	
	

}
