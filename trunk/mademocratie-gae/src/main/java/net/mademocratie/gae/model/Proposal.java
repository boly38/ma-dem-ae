package net.mademocratie.gae.model;

import com.google.appengine.api.users.User;

import javax.jdo.annotations.*;
import java.io.Serializable;
import java.util.Date;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
@Query(name="latestProposals",
       value="select from net.mademocratie.gae.model.Proposal order by date desc range 0,5")
public class Proposal implements Serializable {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 2162676902409015528L;
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private User author;
    
    @Persistent(nullValue = NullValue.EXCEPTION)
    private String title;
    
    @Persistent
    private String content;

    @Persistent(nullValue = NullValue.EXCEPTION)
    private Date date;
    
	public Proposal() {
		super();
	}
	public Proposal(String title, String content) {
		super();
		this.title = title;
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("proposal[");
        sb.append("id:").append(id);
        sb.append(", title:").append(title);
        sb.append(", author:").append(author);
        sb.append(", content:").append(content);
        sb.append("]");
        return sb.toString();
    }
}