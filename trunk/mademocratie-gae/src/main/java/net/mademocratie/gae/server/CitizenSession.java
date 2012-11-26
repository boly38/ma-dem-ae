package net.mademocratie.gae.server;

import com.google.inject.Inject;
import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.server.service.IManageCitizen;
import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

import java.util.logging.Logger;

public class CitizenSession extends AuthenticatedWebSession {
    private static final transient Logger LOGGER = Logger.getLogger(CitizenSession.class.getName());

    private Citizen citizen;

    @Inject
    private transient IManageCitizen manageCitizen;

    private String feedbackSuccess;

    public CitizenSession(Request request) {
        super(request);
    }

    @Override
    public final boolean authenticate(final String email, final String password) {
        citizen = manageCitizen.authenticateCitizen(email, password);
        boolean authenticated = citizen != null;
        LOGGER.info("authenticated="+authenticated);
        setCitizen(citizen);
        return authenticated;
    }

    @Override
    public Roles getRoles() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * static method to get the session from everywhere without cast
     * @return CitizenSession
     */
    public static CitizenSession get() {
        return (CitizenSession) Session.get();
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

    public IManageCitizen getManageCitizen() {
        return manageCitizen;
    }

    public void setManageCitizen(IManageCitizen manageCitizen) {
        this.manageCitizen = manageCitizen;
    }
}
