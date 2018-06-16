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

public class BeanTweet implements Serializable {

	private static final long serialVersionUID = 1L;

	// Mandatory Fields
	private int tweetID;
	private String author;
	private int likes;
	private String message;
	private String publishDate;
	private String originalAuthor;
	private int originalID;

	public BeanTweet() {
		this.tweetID = -1;
		this.author = "";
		this.likes = 0;
		this.message = "";
		this.publishDate = "";
		this.originalAuthor = "";
		this.originalID = -1;
	}

	/* Getters */
	
	public int getTweetID() {
		return this.tweetID;
	}

	public String getAuthor() {
		return this.author;
	}

	public int getLikes() {
		return this.likes;
	}

	public String getMessage() {
		return this.message;
	}

	public String getPublishDate() {
		return this.publishDate;
	}
	
	public String getOriginalAuthor() {
		return this.originalAuthor;
	}
	
	public int getOriginalID() {
		return this.originalID;
	}


	/* Setters */
	
	public void setTweetID(int tweetID) {
		this.tweetID = tweetID;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setOriginalAuthor(String originalAuthor) {
		this.originalAuthor = originalAuthor;
	}
	
	public void setOriginalID(int originalID) {
		this.originalID = originalID;
	}
	
	public void setPublishDate(int year, int month, int day) { 
		System.out.println("Filling birthDate field");
		Date date = new Date(year - 1900, month-1, day); //Date constructor set the date to 1900 + year by default!
		SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
		String stringDate = ymdFormat.format(date);
		//System.out.println("My date formatted: " + stringDate);
		this.publishDate = stringDate;
	}

	public void setPublishDate(String date) {
		System.out.println("Filling birthDate field");
		String[] splitDate = date.split("/");
		String year = splitDate[2];
		String month = splitDate[1];
		String day = splitDate[0];
		
		this.publishDate = year + "-" + month + "-" + day;
	}

	@Override
	public String toString() {
		String string = "Tweet ID: " + String.valueOf(this.tweetID) + "\n";
		string += "Author: " + this.author + "\n Likes: " + String.valueOf(this.likes) + "\n";
		string += "Message: " + this.message + "\n Publish Date: " + this.publishDate + "\n";
		string += "Original Author" + this.originalAuthor;
		return string;
	}
	
	

}
