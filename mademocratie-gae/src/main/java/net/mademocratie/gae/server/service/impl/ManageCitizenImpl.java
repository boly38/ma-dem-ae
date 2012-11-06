package net.mademocratie.gae.server.service.impl;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.Inject;
import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.server.CitizenSession;
import net.mademocratie.gae.server.exception.CitizenAlreadyExistsException;
import net.mademocratie.gae.server.exception.MaDemocratieException;
import net.mademocratie.gae.server.exception.RegisterFailedException;
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
    private static final String MADEM_FROM_NAME = "MaDémocratie";


    @Override
    public Citizen suggestCitizen() {
        User user = userService.getCurrentUser();
        Citizen suggestCitizen = new Citizen();
        if (user != null) {
            suggestCitizen = new Citizen(user.getNickname(), user, "", user.getEmail(), "");
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


    public String generateRandomString(int size) {
        SecureRandom random = new SecureRandom();
        String str = new BigInteger(130, random).toString(32);
        if (str != null && str.length()>size) {
            return str.substring(0,size);
        }
        return str;
    }

    private Citizen registerCitizen(String pseudo, String email, User googleUser) throws RegisterFailedException {
        Citizen newCitizen = new Citizen(pseudo, googleUser, generateRandomString(10), email, generateRandomString(32));
        try {
            addCitizen(newCitizen);
        } catch (CitizenAlreadyExistsException e) {
            LOGGER.warning("CitizenAlreadyExistsException while register citizen " + newCitizen.toString() + " : " + e.getMessage());
            throw new RegisterFailedException("Unable to register with this email, you should already been registered.");
        }
        return newCitizen;
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

    @Override
    public Citizen register(String pseudo, User googleUser) throws RegisterFailedException {
        return registerCitizen(pseudo, googleUser.getEmail(), googleUser);
    }

    @Override
    public Citizen register(String pseudo, String email) throws RegisterFailedException {
        return registerCitizen(pseudo, email, null);
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
