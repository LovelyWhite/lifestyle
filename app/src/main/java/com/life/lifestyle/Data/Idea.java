package com.life.lifestyle.Data;

import java.sql.Blob;

public class Idea {

	private String ideaID;
	private String userID;
	private String date;
	private Blob information;
	public String getIdeaID() {
		return ideaID;
	}

	public void setIdeaID(String ideaID) {
		this.ideaID = ideaID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Blob getInformation() {
		return information;
	}

	public void setInformation(Blob information) {
		this.information = information;
	}

}