package models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

//import java.text.SimpleDateFormat;
import utils.GeneralUtils;

public class BeanUser implements Serializable {

	private static final long serialVersionUID = 1L;

	// Mandatory Fields
	private String name = "";
	private String surname = "";
	private String mail = ""; 
	private String user = ""; 
	private String password = "";
    private String birthDate = "";
	

	// Optional Fields
	private String description = "";
	private String gender = "";
	private List<String> userConsoles; 
	private List<String> gameGenres; 
	private String youtubeChannelID = "";
	private String twitchChannelID = "";
	private boolean isAdmin;

	public BeanUser() {
		userConsoles = new LinkedList<String>();
		gameGenres = new LinkedList<String>();
		this.isAdmin = false;
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
	
	public boolean getIsAdmin() {
		return this.isAdmin;
	}

	/* Setters */

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setBirthDate(int year, int month, int day) { 
		Date date = new Date(year - 1900, month-1, day); 
		SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
		String stringDate = ymdFormat.format(date);
		this.birthDate = stringDate;
	}
	
	public void setBirthDate(String date) {
		String[] splitDate = date.split("/");
		String year = splitDate[2];
		String month = splitDate[1];
		String day = splitDate[0];
		
		this.birthDate = year + "-" + month + "-" + day;
	}
	
	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setUserConsoles(List<String> userConsoles) {
		this.userConsoles = userConsoles;
	}

	public void setGameGenres(List<String> gameGenres) {
		this.gameGenres = gameGenres;
	}

	public void setYoutubeChannelID(String youtubeChannelID) {
		this.youtubeChannelID = youtubeChannelID;
	}

	public void setTwitchChannelID(String twitchChannelID) {
		this.twitchChannelID = twitchChannelID;
	}
	
	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String toString() {
		return "Name: " + this.getName() + "\n" + "Surname: "+this.getSurname() + "\n" + "User: " +this.getUser() + "\n" + "Password:" +this.getPassword()
				+ "\n" + "Gender: " + this.getGender() + "\n" + "Mail: " + this.getMail() + "\n" + "Description: " + this.getDescription() + "\n"
				+ "BirthDate: " + this.getBirthDate() + "\n" + "GameGenres: " +this.getGameGenres() + "\n" + "UserConsoles:" + this.getUserConsoles() + "\n"
				+ "TwitchID: " + this.getTwitchChannelID() + "\n" + "YoutubeID: " +this.getYoutubeChannelID() + "\n";
	}

}
