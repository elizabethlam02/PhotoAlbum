package model;


/**
 * 
 * @author Himani Patel
 * @author Elizabeth Lam
 *
 */

public class Tag {
	
	/**
	 * Name of the tag
	 */
	public String name;
	/**
	 * Value of the Tag
	 */
	public String value;
	
	
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
	 * Compares tag values
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
		return name + " , " + value; 
	} 
	
	

}
