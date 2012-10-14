package net.mademocratie.gae.server.service;

import com.google.inject.ImplementedBy;
import net.mademocratie.gae.model.Citizen;
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
     * add a new citizen to the database
     * @param inputCitizen
     */
    void addCitizen(Citizen inputCitizen);

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
}
