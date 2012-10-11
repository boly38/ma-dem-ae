package net.mademocratie.gae.server.service;

import net.mademocratie.gae.model.Citizen;

import java.util.List;

public interface ICitizen {
    /**
     * Return the latest citizens, ordered by descending subscription date.
     *
     * @param max the maximum number of citizens to return
     * @return the citiens
     */
    List<Citizen> latest(int max);
}
