package net.mademocratie.gae.server.service;

import com.google.appengine.api.users.User;
import com.google.inject.ImplementedBy;
import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.server.CitizenSession;
import net.mademocratie.gae.server.exception.MaDemocratieException;
import net.mademocratie.gae.server.exception.RegisterFailedException;
import net.mademocratie.gae.server.service.impl.ManageCitizenImpl;

import java.util.List;

@ImplementedBy(ManageCitizenImpl.class)
public interface IManageCitizen {
    /**
     * suggest Citizen attributes by analysing current session.
     * @return
     */
    Citizen suggestCitizen();

    /**
     * Return the latest citizens, ordered by descending date.
     *
     * @param max the maximum number of citizens to return
     * @return the citizens
     */
    List<Citizen> latest(int max);

    /**
     * remove all citizen from the repository (test usage only)
     */
    void removeAll();

    /**
     * Authenticate a citizen and return it (or null)
     * @param email
     * @param password
     * @return
     */
    Citizen authenticateCitizen(String email, String password);

    /**
     * Sign in a citizen an store it into the given session or return false;
     * @param session
     * @param email
     * @param password
     * @return
     */
    boolean signInCitizen(CitizenSession session, String email, String password);

    User getGoogleUser();

    String getGoogleLoginURL(String destination);
    String getGoogleLogoutURL(String destination);

    Citizen register(String pseudo, User googleUser) throws RegisterFailedException;
    Citizen register(String pseudo, String email) throws RegisterFailedException;
    void registerNotifyCitizen(Citizen justRegisteredCitizen, String activateDestination) throws MaDemocratieException;

    Citizen getById(Long cId);
}
