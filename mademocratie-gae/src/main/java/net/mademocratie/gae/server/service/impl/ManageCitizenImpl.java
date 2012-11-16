package net.mademocratie.gae.server.service.impl;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.Inject;
import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.model.CitizenState;
import net.mademocratie.gae.server.CitizenSession;
import net.mademocratie.gae.server.exception.*;
import net.mademocratie.gae.server.service.ICitizen;
import net.mademocratie.gae.server.service.IManageCitizen;
import net.mademocratie.gae.server.service.IRepository;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Properties;
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
    private static final String MADEM_FROM_EMAIL = "info@mademocratie.net";
    private static final String MADEM_FROM_NAME = "MaDemocratie";


    @Override
    public Citizen suggestCitizen() {
        User user = userService.getCurrentUser();
        Citizen suggestCitizen = new Citizen();
        if (user != null) {
            suggestCitizen = new Citizen(user.getNickname(), user);
        }
        return suggestCitizen;
    }

    /**
     * add a new citizen to the database
     * @param inputCitizen citizen to add
     */
    Citizen addCitizen(Citizen inputCitizen) throws CitizenAlreadyExistsException {
        if (citizensQueries.findByEmail(inputCitizen.getEmail()) != null) {
            throw new CitizenAlreadyExistsException();
        }
        citizenRepo.persist(inputCitizen);
        return inputCitizen;
    }

    @Override
    public List<Citizen> latest(int max) {
        return citizensQueries.latest(max);
    }

    @Override
    public Citizen authenticateCitizen(String email, String password) {
        Citizen citizen = citizensQueries.findByEmail(email);
        if (citizen == null) {
            LOGGER.warning("authenticateCitizen unable to find user with this email : " + email);
            return null;
        }
        if (citizen.getGoogleUser() != null || citizen.isPasswordEqualsTo(password)) {
            LOGGER.warning("authenticateCitizen with this email : " + email);
            return citizen;
        }
        LOGGER.warning("authenticateCitizen unable to match password for this email : " + email);
        return null;
    }

    @Override
    public boolean signInCitizen(String email, String password) {
        // Get session info
        CitizenSession session = CitizenSession.get();

        if (session.getManageCitizen() == null) {
            // TODO : find a better way to inject manager
            LOGGER.warning("setManageCitizen");
            session.setManageCitizen(this);
        }
        boolean authenticated = session.signIn(email, password);
        if (authenticated) {
            LOGGER.info(email + " sign in");
            return true;
        }
        LOGGER.info("unable to sign in with the following email : " + email);
        return false;
    }

    @Override
    public boolean signInGoogleCitizen() {
        User user = userService.getCurrentUser();
        return (user != null) && signInCitizen(user.getEmail(), null);
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


    public String generateRandomString(int size) {
        SecureRandom random = new SecureRandom();
        String str = new BigInteger(130, random).toString(32);
        if (str != null && str.length()>size) {
            return str.substring(0,size);
        }
        return str;
    }

    public void registerNotifyCitizen(Citizen justRegisteredCitizen, String activateDestination) throws MaDemocratieException {
        sendMail(justRegisteredCitizen.getEmail(),
                 justRegisteredCitizen.getPseudo(),
                "Welcome on MaDemocratie.net",
                "To complete your registration, please follow this link : " + activateDestination);
    }

    private void sendMail(String toEmail, String toString, String title, String body) throws MaDemocratieException {
        Properties props = new Properties();
        // props.put("mail.smtp.host", "smtp");
        // props.put("mail.smtp.port", 25);
        Session session = Session.getDefaultInstance(props);
        Message msg = new MimeMessage(session);
        String sendMailLogStr = "sendMail to " + toEmail + " title=" + title + " body=" + body;
        try {
            msg.setFrom(new InternetAddress(MADEM_FROM_EMAIL, MADEM_FROM_NAME));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(toEmail, toString));
            msg.setSubject(title);
            msg.setText(body);
            Transport.send(msg);
            LOGGER.info(sendMailLogStr);
        } catch (MessagingException e) {
            LOGGER.severe("Exception while " + sendMailLogStr + ": " + e.getMessage());
            e.printStackTrace();
            throw new MaDemocratieException("Unable to send mail ; registration process is broken, please report this error to the support.");
        } catch (UnsupportedEncodingException e) {
            LOGGER.severe("Exception while " + sendMailLogStr + ": " + e.getMessage());
            e.printStackTrace();
            throw new MaDemocratieException("Unable to send mail ; registration process is broken, please report this error to the support.");
        }
    }

    private void handleCitizenAlreadyExistWhenRegister(CitizenAlreadyExistsException e, String citizenDescription) throws RegisterFailedException {
        LOGGER.warning("CitizenAlreadyExistsException while register citizen " + citizenDescription + " : " + e.getMessage());
        throw new RegisterFailedException("Unable to register with this email, you should already been registered.");
    }

    @Override
    public Citizen register(String pseudo, User googleUser) throws RegisterFailedException {
        Citizen newCitizen = new Citizen(pseudo, googleUser);
        try {
            addCitizen(newCitizen);
        } catch (CitizenAlreadyExistsException e) {
            handleCitizenAlreadyExistWhenRegister(e, newCitizen.toString());
        }
        return newCitizen;
    }

    @Override
    public Citizen register(String pseudo, String email) throws RegisterFailedException {
        Citizen newCitizen = new Citizen(pseudo, generateRandomString(10), email, generateRandomString(32));
        try {
            addCitizen(newCitizen);
        } catch (CitizenAlreadyExistsException e) {
            handleCitizenAlreadyExistWhenRegister(e, newCitizen.toString());
        }
        return newCitizen;

    }

    @Override
    public Citizen getById(Long cId) {
        return citizenRepo.get(cId);
    }

    @Override
    public Citizen activateCitizenByKey(Long cId, String activationKey) throws DeprecatedActivationLinkException, WrongActivationLinkException {
        Citizen citizen = getById(cId);
        activationCheckCitizenState(citizen);
        if (activationKey != null && citizen.getCitizenStateData().equals(activationKey)) {
            activateCitizen(citizen);
            return citizen;
        } else {
            throw new WrongActivationLinkException();
        }
    }

    private void activateCitizen(Citizen citizenToActivate) {
        citizenToActivate.setCitizenState(CitizenState.ACTIVE);
        citizenToActivate.setCitizenStateData((new Date()).toString());
        citizenRepo.persist(citizenToActivate);
    }

    private void activationCheckCitizenState(Citizen justRegisteredCitizen) throws DeprecatedActivationLinkException {
        if (justRegisteredCitizen == null) {
            LOGGER.warning("deprecated activation link (citizen null)");
            throw new DeprecatedActivationLinkException();
        }
        CitizenState citizenState = justRegisteredCitizen.getCitizenState();
        if (citizenState == null) {
            LOGGER.warning("activation link of a wrong state citizen " + justRegisteredCitizen);
            throw new DeprecatedActivationLinkException();
        }
        if (CitizenState.SUSPENDED.equals(citizenState)) {
            LOGGER.warning("activation link of a suspended citizen " + justRegisteredCitizen);
            throw new DeprecatedActivationLinkException();
        }
        if (CitizenState.REMOVED.equals(citizenState)) {
            LOGGER.warning("activation link of a removed citizen " + justRegisteredCitizen);
            throw new DeprecatedActivationLinkException();
        }
        if (CitizenState.ACTIVE.equals(citizenState)) {
            LOGGER.warning("activation link of an active citizen " + justRegisteredCitizen);
            throw new DeprecatedActivationLinkException();
        }
    }

    private void changePasswordCheckCitizenState(Citizen citizen) throws ChangePasswordException {
        if (citizen == null) {
            LOGGER.warning("unable to change password of a null citizen");
            throw new ChangePasswordException();
        }
        CitizenState citizenState = citizen.getCitizenState();
        if (citizenState == null) {
            LOGGER.warning("unable to change password of a wrong state citizen " + citizen);
            throw new ChangePasswordException();
        }
        if (CitizenState.SUSPENDED.equals(citizenState)) {
            LOGGER.warning("unable to change password of a suspended citizen " + citizen);
            throw new ChangePasswordException("your account has been suspended, please contact administrator.");
        }
        if (CitizenState.REMOVED.equals(citizenState)) {
            LOGGER.warning("\"unable to change password of a removed citizen " + citizen);
            throw new ChangePasswordException("your account doesn't no more exist");
        }
    }


    @Override
    public void changeCitizenPassword(Long cId, String newPassword) throws ChangePasswordException {
        Citizen citizen = getById(cId);
        changePasswordCheckCitizenState(citizen);
        citizen.setPassword(newPassword);
        citizenRepo.persist(citizen);
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
