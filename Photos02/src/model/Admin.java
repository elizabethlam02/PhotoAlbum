package model;
import java.io.File;

import java.io.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

/**
 * This class store methods used by the Admin user
 * @author Himani Patel
 * @author Elizabeth Lam
 *
 */

public class Admin implements Serializable{
	
	
	/**
	 * Serializable Interface used to store User Data
	 */
	private static final long serialVersionUID = 1L;
	public static final String storeDir = "dat";
	public static final String storeFile = "users.dat";

	/**
	 * Current User
	 */
	public static User currentUser;
	
	//public boolean userOn;
	/**
	 * List of users
	 */
	public ArrayList<User> users; 
	/**
	 * returns true if stock files are already added
	 */
	public boolean stockAdded = false;
	
	/**
	 *Singleton instance of admin
	 */
	private static Admin instance = null;
	/**
	 * Admin Constructor
	 */
	public Admin(){
		users = new ArrayList<User>();
		users.add(new User("stock"));
		currentUser = null;
		//userOn = false;
		
	}
	/**
	 * returns Instance of Admin class
	 */
	public static Admin getInstance(){
		if (instance == null){
			instance = new Admin();
		}
		return instance;
	}
	/**
	 * returns list of Users
	 * @return users
	 */
	public ArrayList<User> getUsers(){
		return this.users;
	}
	/**
	 * creates new User to the ArrayList of users by given username
	 * @param username
	 */
	public void createUser(String username) {
		users.add(new User(username));
	}
	/**
	 * deletes a user by String username
	 * @param username
	 */
	public void deleteUser(String username) {
		for (User u: users) {
			if(u.getUsername().equalsIgnoreCase(username)) {
				int index = users.indexOf(u);
				users.remove(index);
			}
		}
	}
	/**
	 * deletes a user by given index
	 * @param index
	 */
	public void deleteUser(int index) {
		users.remove(index);
	}
	/**
	 * checks if a User exists with the same name
	 * @param name
	 * @return
	 */
	public boolean userExists(String name) {
		for (User u: users) {
			//System.out.println(u.username);
			if (u.getUsername().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
		
	}
	/**
	 * returns user instance by username
	 * @param username
	 * @return
	 */
	public User getUser(String username) {
		for (User u: users) {
			if(u.getUsername().equals(username)) {
				return u;
			}
		}
		return null;
	}
	/**
	 * gets User by index
	 * @param index
	 */
	public User getUser(int index) {
		return users.get(index);
	}
	/**
	 * gets the current user
	 * @return
	 */
	public User getCurrent() {
		return currentUser;
	}
	/**
	 * sets the currentUser
	 * @param current
	 */
	public void setCurrent(User current) {
		Admin.currentUser = current;
		//System.out.println("True");
	}

	/*
	public boolean checkUser(String username) {
		int index = -1;
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getUsername().equals(username)) {
				index = i;
			}
		}
		
		if(index != -1) {
			this.setCurrent(users.get(index));
			this.userOn = true;
			return true;
		
		}
		return false;
		
	}
	*/
	/**
	 * Used to store data of the Admin instance
	 * @param Adapp
	 * @throws IOException
	 */
	public static void save(Admin Adapp) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(Adapp);
		oos.close();
	}
	/**
	 * Used to load data for the Admin instance
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Admin load() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		Admin userList = (Admin) ois.readObject();
		ois.close();
		return userList;
		
	}
}
