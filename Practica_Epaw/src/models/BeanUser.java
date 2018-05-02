package models;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

//import java.text.SimpleDateFormat;
import utils.GeneralUtils;

public class BeanUser implements Serializable  {

	private static final long serialVersionUID = 1L;
	/*  Control which parameters have been correctly filled */
	private int[] error = {0,0}; 
	
	// Mandatory Fields
	private String name = "";
	private String surname = "";
	private String mail = ""; //In our seminar, our email
	private String birthDate = "";
	private String user = ""; //In our seminar, our username
	private String password = "";
	
	// Optional Fields
	private String description = "";
	private String gender = "";
	/*Utilitzar src.utils.GeneralUtils per les llistes*/
	private List<String> userConsoles = new LinkedList<String>();
	private List<String> gameGenres = new LinkedList<String>();
	private String youtubeChannelID = "";
	private String twitchChannelID = "";
	
	public BeanUser() {}
	
	/* Getters */
	
	public int[] getError() {
		return error;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getMail() {
		return mail;
	}
	
	public String getBirthDate() {
		return birthDate;
	}
	
	public String getUser(){
		return user;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getGender() {
		return gender;
	}
	
	public List<String> getUserConsoles() {
		return userConsoles;
	}
	
	public List<String> getGameGenres() {
		return gameGenres;
	}
	
	public String getYoutubeChannelID() {
		return youtubeChannelID;
	}
	
	public String getTwitchChannelID() {
		return twitchChannelID;
	}
	
	/* Setters */
	
	public void setName(String name) {
		System.out.println("Filling name field");
		this.name = name;
	}

	public void setSurname(String surname) {
		System.out.println("Filling surname field");
		this.surname = surname;
	}
	
	public void setMail(String mail){
		System.out.println("Filling mail field");
		this.mail = mail;
	}
	
	public void setBirthDate(String birthDate) {
		System.out.println("Filling birthDate field");
		this.birthDate = birthDate;
	}
	
	public void setUser(String user){
		
		System.out.println("Filling user field");
		/* We simulate a user with the same unsername exists in our DB */
		error[0] = 1;
		
	}
	
	public void setPassword(String password) {
		System.out.println("Filling password field");
		this.password = password;
	}
	
	public void setDescription(String description) {
		System.out.println("Filling description field");
		this.description = description;
	}

	public void setGender(String gender) {
		System.out.println("Filling gender field");
		this.gender = gender;
	}

	public void setUserConsoles(List<String> userConsoles) {
		System.out.println("Filling userConsoles field");
		this.userConsoles = userConsoles;
	}

	public void setGameGenres(List<String> gameGenres) {
		System.out.println("Filling gameGenres field");
		this.gameGenres = gameGenres;
	}

	public void setYoutubeChannelID(String youtubeChannelID) {
		System.out.println("Filling youtubeChanneldID field");
		this.youtubeChannelID = youtubeChannelID;
	}

	public void setTwitchChannelID(String twitchChannelID) {
		System.out.println("Filling youtubeChannelID field");
		this.twitchChannelID = twitchChannelID;
	}
	
	/* Logic Functions */
	
	/*Check if all the fields are filled correctly */
	/*TODO: es aqui on hauriem de fer la validacio?*/
	
	public boolean isComplete() {
	    return(hasValue(getUser()) &&
	           hasValue(getMail()) );
	}
	
	private boolean hasValue(String val) {
		return((val != null) && (!val.equals("")));
	}
}
