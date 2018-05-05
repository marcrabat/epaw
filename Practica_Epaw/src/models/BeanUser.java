package models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

//import java.text.SimpleDateFormat;
import utils.GeneralUtils;

public class BeanUser implements Serializable {

	private static final long serialVersionUID = 1L;

	// Mandatory Fields
	private String name = "";
	private String surname = "";
	private String mail = ""; // In our seminar, our email
	private String user = ""; // In our seminar, our username
	private String password = "";
    private String birthDate = "";
	

	// Optional Fields
	private String description = "";
	private String gender = "";
	private List<String> userConsoles; 
	private List<String> gameGenres; 
	private String youtubeChannelID = "";
	private String twitchChannelID = "";

	public BeanUser() {
		userConsoles = new LinkedList<String>();
		gameGenres = new LinkedList<String>();
	}

	/* Getters */

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

	public String getUser() {
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

	public void setMail(String mail) {
		System.out.println("Filling mail field");
		this.mail = mail;
	}

	public void setBirthDate(int year, int month, int day) { 
		System.out.println("Filling birthDate field");
		Date date = new Date(year - 1900, month-1, day); //Date constructor set the date to 1900 + year by default!
		SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
		String stringDate = ymdFormat.format(date);
		//System.out.println("My date formatted: " + stringDate);
		this.birthDate = stringDate;
	}

	public void setUser(String user) {
		System.out.println("Filling user field");
		this.user = user;
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
		System.out.println("Filling twitchChannelID field");
		this.twitchChannelID = twitchChannelID;
	}

	public String toString() {
		return "Name: " + this.getName() + "\n" + "Surname: "+this.getSurname() + "\n" + "User: " +this.getUser() + "\n" + "Password:" +this.getPassword()
				+ "\n" + "Gender: " + this.getGender() + "\n" + "Mail: " + this.getMail() + "\n" + "Description: " + this.getDescription() + "\n"
				+ "BirthDate: " + this.getBirthDate() + "\n" + "GameGenres: " +this.getGameGenres() + "\n" + "UserConsoles:" + this.getUserConsoles() + "\n"
				+ "TwitchID: " + this.getTwitchChannelID() + "\n" + "YoutubeID: " +this.getYoutubeChannelID() + "\n";
	}

}
