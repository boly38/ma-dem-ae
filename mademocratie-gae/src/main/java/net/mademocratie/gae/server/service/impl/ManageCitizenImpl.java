package net.mademocratie.gae.server.service.impl;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.Inject;
import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.model.ImplementationInProgressException;
import net.mademocratie.gae.server.CitizenSession;
import net.mademocratie.gae.server.service.ICitizen;
import net.mademocratie.gae.server.service.IManageCitizen;
import net.mademocratie.gae.server.service.IRepository;

import java.util.List;
import java.util.logging.Logger;


/**
 * @DevInProgress
 */
public class ManageCitizenImpl implements IManageCitizen {
    private final static Logger LOGGER = Logger.getLogger(ManageCitizenImpl.class.getName());
    // ~services
    @Inject
    private ICitizen citizensQueries;
    @Inject
    private IRepository<Citizen> citizenRepo;

    private UserService userService = UserServiceFactory.getUserService();


    @Override
    public Citizen suggestCitizen() {
        User user = userService.getCurrentUser();
        Citizen suggestCitizen = new Citizen();
        if (user != null) {
            suggestCitizen = new Citizen(user, user.getNickname(), "", user.getEmail(), "");
        }
        return suggestCitizen;
    }

    /**
     * add a new citizen to the database
     * @param inputCitizen
     */
    void addCitizen(Citizen inputCitizen) {
        citizenRepo.persist(inputCitizen);
    }

    @Override
    public List<Citizen> latest(int max) {
        return citizensQueries.latest(max);
    }

    @Override
    public void removeAll() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Citizen authenticateCitizen(String email, String password) {
        Citizen citizen = citizensQueries.findByEmail(email);
        if (citizen != null
         && citizen.isPasswordEqualsTo(password)) {
            return citizen;
        }
        return null;
    }

    @Override
    public boolean signInCitizen(CitizenSession session, String email, String password) {
        Citizen authCitizen =  authenticateCitizen(email, password);
        if (authCitizen != null) {
            LOGGER.info(authCitizen.getEmail() + " sign in");
            session.setCitizen(authCitizen);
            return true;
        }
        LOGGER.info("unable to sign in with the following email : " + email);
        return false;
    }

    @Override
    public User getGoogleUser() {
        return userService.getCurrentUser();
    }

    @Override
    public String getGoogleLoginURL(String destination) {
        return userService.createLoginURL(destination);
    }

    @Override
    public String getGoogleLogoutURL(String destination) {
        return userService.createLogoutURL(destination);
    }

    @Override
    public void register(String pseudo, User googleUser) throws ImplementationInProgressException {
        throw new ImplementationInProgressException("dev in progress...");
    }

    @Override
    public void register(String pseudo, String email) throws ImplementationInProgressException {
        throw new ImplementationInProgressException("dev in progress...");
    }

    //~ getters && setters

    public void setCitizensQueries(ICitizen citizensQueries) {
        this.citizensQueries = citizensQueries;
    }

    public void setCitizenRepo(IRepository<Citizen> citizenRepo) {
        this.citizenRepo = citizenRepo;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
