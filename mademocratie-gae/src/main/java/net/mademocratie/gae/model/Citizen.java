package net.mademocratie.gae.model;

import com.google.appengine.api.users.User;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.NullValue;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.io.Serializable;

public class Citizen implements Serializable {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private User googleUser;

    @Persistent(nullValue = NullValue.EXCEPTION)
    private String pseudo;

    @Persistent(nullValue = NullValue.EXCEPTION)
    private String password;

    @Persistent
    private String email;

    @Persistent
    private String location;
}
