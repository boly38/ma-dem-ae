package net.mademocratie.gae.model;

import com.google.appengine.api.datastore.Text;

import javax.jdo.annotations.*;
import java.io.Serializable;
import java.util.Date;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
// @Query(name="latestProposals",
//       value="select from net.mademocratie.gae.model.Proposal order by date desc range 0,5")
public class Proposal implements Serializable {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 2162676902409015528L;
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private String authorEmail = null;

    @Persistent
    private String authorPseudo = null;

    @Persistent(nullValue = NullValue.EXCEPTION)
    private String title;
    
    @Persistent
    private Text content;

    @Persistent(nullValue = NullValue.EXCEPTION)
    private Date date;
    
	public Proposal() {
		super();
	}
	public Proposal(String title, String content) {
		super();
		this.title = title;
		this.content = new Text(content);
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return (content != null ? content.getValue():null);
	}
	public void setContent(String content) {
		this.content = new Text(content);
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

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getAuthorPseudo() {
        return authorPseudo;
    }

    public void setAuthorPseudo(String authorPseudo) {
        this.authorPseudo = authorPseudo;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("proposal[");
        sb.append("id:").append(id);
        sb.append(", title:").append(title);
        if (authorPseudo != null)
            sb.append(", authorPseudo:").append(authorPseudo);
        if (authorEmail != null)
            sb.append(", authorEmail:").append(authorEmail);
        sb.append(", content:").append(content);
        sb.append("]");
        return sb.toString();
    }

    public void setAuthor(Citizen author) {
        if (author != null) {
            setAuthorPseudo(author.getPseudo());
            setAuthorEmail(author.getEmail());
        }
    }
}