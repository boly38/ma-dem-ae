package com.appspot.mademea.model;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Proposal implements Serializable {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 2162676902409015528L;

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    // @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private Long id;
	
    @Persistent
	private String title;
    
    @Persistent
	private String description;
    
    @Persistent
    public static final String PROPOSAL_CREATION_DATE ="creationDate";
	private Date creationDate;
    
    @Persistent
	private String author;
	
	public Proposal() {
		super();
	}
	public Proposal(String title, String description) {
		super();
		this.creationDate = new Date();
		this.title = title;
		this.description = description;
	}
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
