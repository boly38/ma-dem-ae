package net.mademocratie.gae.model;

import com.google.appengine.api.users.User;

import com.google.appengine.api.datastore.Key;
import javax.jdo.annotations.*;
import java.io.Serializable;
import java.util.Date;


@PersistenceCapable(identityType = IdentityType.APPLICATION)
@Query(name="latestCitizens",
        value="select from net.mademocratie.gae.model.Citizen order by date desc range 0,5")
public class Citizen implements Serializable {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key id;

    @Persistent
    private User googleUser;

    /*
     * citizen creation date
     */
    @Persistent(nullValue = NullValue.EXCEPTION)
    private Date date;

    @Persistent(nullValue = NullValue.EXCEPTION)
    private String pseudo;

    /**
     * citizen password: null if googleuser is set
     */
    @Persistent
    private String password;

    @Persistent
    @Unique
    private String email;

    @Persistent
    private CitizenState citizenState;

    /**
     * depend on citizenState value :
     * case CREATED : citizenStateData = registrationUniqueKey
     * case ACTIVE  : citizenStateData = null
     * case SUSPENDED : citizenStateData = (admin) reason
     * case REMOVED : citizenStateData = date removed, old pseudo used, reason why removed.
     */
    @Persistent
    private String citizenStateData;

    @Persistent
    private String location;

    public Citizen() {
        super();
    }

    public Citizen(String pseudo, User googleUser) {
        this.pseudo = pseudo;
        this.googleUser = googleUser;
        this.email = googleUser.getEmail();
        date = new Date();
        citizenState = CitizenState.ACTIVE;
        citizenStateData = (new Date()).toString();
    }

    public Citizen(String pseudo, String password, String email, String accessKey) {
        this.pseudo = pseudo;
        this.googleUser = null;
        this.password = password;
        this.email = email;
        date = new Date();
        citizenState = CitizenState.CREATED;
        citizenStateData = accessKey;
    }

    public Key getId() {
        return id;
    }

    public void setId(Key id) {
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

    public CitizenState getCitizenState() {
        return citizenState;
    }

    public void setCitizenState(CitizenState citizenState) {
        this.citizenState = citizenState;
    }

    public String getCitizenStateData() {
        return citizenStateData;
    }

    public void setCitizenStateData(String citizenStateData) {
        this.citizenStateData = citizenStateData;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("citizen[");
        sb.append("id:").append(id);
        sb.append(", pseudo:").append(pseudo);
        sb.append(", email:").append(email);
        if (citizenState.equals(CitizenState.CREATED)) {
            sb.append(" *non validated*");
        }
        sb.append(", state:").append(citizenState.toString());
        sb.append(", location:").append(location);
        sb.append("]");
        return sb.toString();
    }

    public boolean isPasswordEqualsTo(String password) {
        return this.password != null && this.password.equals(password);
    }
}
