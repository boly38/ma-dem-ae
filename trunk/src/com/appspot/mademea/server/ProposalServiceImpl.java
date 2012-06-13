package com.appspot.mademea.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;

import com.appspot.mademea.client.ProposalService;
import com.appspot.mademea.shared.Proposal;
import com.appspot.mademea.shared.exception.TooMuchProposalsException;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ProposalServiceImpl extends RemoteServiceServlet implements
										 ProposalService {
	public void addProposal(String title, String description)
			throws IllegalArgumentException, TooMuchProposalsException {
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        Proposal newProposal = new Proposal(title, description);
        if (user != null) {
          newProposal.setAuthor(user.getNickname());
        } else {
          newProposal.setAuthor("Anonymous");	
        }
        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            pm.makePersistent(newProposal);
        } finally {
            pm.close();
        }
	}

	public List<Proposal> getProposals() {
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    String query = "select from " + Proposal.class.getName() + " order by " + Proposal.PROPOSAL_CREATION_DATE +" desc range 0,20";
	    @SuppressWarnings("unchecked")
		List<Proposal> props = (List<Proposal>) pm.newQuery(query).execute();
	    // serialization issue workaround
	    List<Proposal> propsReturned = new ArrayList<Proposal>(props);
	    return propsReturned;
	} 

}
