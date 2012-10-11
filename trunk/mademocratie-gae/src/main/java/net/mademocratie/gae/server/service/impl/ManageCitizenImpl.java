package net.mademocratie.gae.server.service.impl;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.Inject;
import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.server.service.ICitizen;
import net.mademocratie.gae.server.service.IManageCitizen;
import net.mademocratie.gae.server.service.IRepository;


/**
 * @DevInProgress
 */
public class ManageCitizenImpl implements IManageCitizen {
    // ~services
    @Inject
    private ICitizen citizensQueries;
    @Inject
    private IRepository<Citizen> citizenRepo;


    @Override
    public Citizen suggestCitizen() {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        Citizen suggestCitizen = new Citizen();
        if (user != null) {
            suggestCitizen = new Citizen(user, user.getNickname(), "", user.getEmail(), "");
        }
        return suggestCitizen;
    }

    @Override
    public void addCitizen(Citizen inputCitizen) {
        citizenRepo.persist(inputCitizen);
    }

    @Override
    public void removeAll() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setCitizensQueries(ICitizen citizensQueries) {
        this.citizensQueries = citizensQueries;
    }

    public void setCitizenRepo(IRepository<Citizen> citizenRepo) {
        this.citizenRepo = citizenRepo;
    }

}
