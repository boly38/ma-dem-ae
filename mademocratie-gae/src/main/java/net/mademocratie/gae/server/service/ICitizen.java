package net.mademocratie.gae.server.service;

import net.mademocratie.gae.model.Citizen;

import java.util.List;

public interface ICitizen {
    /**
     * find and return a citizen by email
     * @param email
     * @return citizen or null if not found
     */
    Citizen findByEmail(String email);

    /**
     * Return the latest citizens, ordered by descending subscription date.
     *
     * @param max the maximum number of citizens to return
     * @return the citiens
     */
    List<Citizen> latest(int max);
}
