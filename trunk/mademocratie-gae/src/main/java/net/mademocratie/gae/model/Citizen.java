package net.mademocratie.gae.model;

import com.google.appengine.api.users.User;

import javax.jdo.annotations.*;
import java.io.Serializable;
import java.util.Date;


@PersistenceCapable(identityType = IdentityType.APPLICATION)
@Query(name="latestCitizens",
        value="select from net.mademocratie.gae.model.Citizen order by date desc range 0,5")
public class Citizen implements Serializable {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private User googleUser;

    /*
     * citizen creation date
     */
    @Persistent(nullValue = NullValue.EXCEPTION)
    private Date date;

    @Persistent(nullValue = NullValue.EXCEPTION)
    private String pseudo;

    @Persistent(nullValue = NullValue.EXCEPTION)
    private String password;

    @Persistent
    @Unique
    private String email;

    private boolean emailValidated = false;

    @Persistent
    private String location;

    public Citizen() {
        super();
        date = new Date();
    }

    public Citizen(User googleUser, String pseudo, String password, String email, String location) {
        date = new Date();
        this.googleUser = googleUser;
        this.pseudo = pseudo;
        this.password = password;
        this.email = email;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getGoogleUser() {
        return googleUser;
    }

    public void setGoogleUser(User googleUser) {
        this.googleUser = googleUser;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isEmailValidated() {
        return emailValidated;
    }

    public void setEmailValidated(boolean emailValidated) {
        this.emailValidated = emailValidated;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("citizen[");
        sb.append("id:").append(id);
        sb.append(", pseudo:").append(pseudo);
        sb.append(", email:").append(email);
        if (!emailValidated) {
            sb.append(" *non validated*");
        }
        sb.append(", location:").append(location);
        sb.append("]");
        return sb.toString();
    }

    public boolean isPasswordEqualsTo(String password) {
        return this.password != null && this.password.equals(password);
    }
}
