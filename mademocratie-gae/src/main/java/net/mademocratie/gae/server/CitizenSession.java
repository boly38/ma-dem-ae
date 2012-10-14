package net.mademocratie.gae.server;

import com.google.inject.Inject;
import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.server.service.IManageCitizen;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

public class CitizenSession extends AuthenticatedWebSession {
    private Citizen citizen;

    // TODO : find why inject does work here
    @Inject
    private IManageCitizen manageCitizen;

    public CitizenSession(Request request) {
        super(request);
    }

    @Override
    public final boolean authenticate(final String email, final String password) {
        citizen = manageCitizen.authenticateCitizen(email, password);
        return citizen != null;
    }

    @Override
    public Roles getRoles() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Citizen getCitizen() {
        return citizen;
    }
}
