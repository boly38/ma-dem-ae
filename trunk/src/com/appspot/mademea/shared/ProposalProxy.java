package com.appspot.mademea.shared;

import java.io.Serializable;
import java.util.Date;

public class ProposalProxy implements Serializable {
    /**
	 * serialUID
	 */
	private static final long serialVersionUID = -3055088271357139222L;
	
	private Long id;
	private String title;
	private String description;
	private Date creationDate;
	private String author;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
