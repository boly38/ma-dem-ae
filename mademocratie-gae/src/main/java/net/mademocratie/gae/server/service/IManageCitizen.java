package net.mademocratie.gae.server.service;

import net.mademocratie.gae.model.Citizen;

public interface IManageCitizen {
    /**
     * suggest Citizen attributes by analysing current session.
     * @return
     */
    public Citizen suggestCitizen();
    /**
     * add a new citizen to the database
     * @param inputCitizen
     */
    public void addCitizen(Citizen inputCitizen);
}
