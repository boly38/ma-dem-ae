package net.mademocratie.gae.client;

import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.server.CitizenSession;
import org.apache.wicket.markup.html.panel.Panel;

import java.util.logging.Logger;

/**
 * ProposalVote
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
public class ProposalVote extends Panel {
    private final static Logger LOGGER = Logger.getLogger(ProposalVote.class.getName());

    public ProposalVote(String id) {
        super(id);
        Citizen currentUser = CitizenSession.get().getCitizen();
    }
}
