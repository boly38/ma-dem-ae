package com.appspot.mademea.client.domain;

import java.io.Serializable;
import java.util.Date;

public class Proposal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2162676902409015528L;
	private String title;
	private String description;
	private Date creationDate;
	private String author;
	
	public Proposal() {
		super();
	}
	public Proposal(String title, String description, String author) {
		super();
		this.creationDate = new Date();
		this.title = title;
		this.description = description;
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
}
