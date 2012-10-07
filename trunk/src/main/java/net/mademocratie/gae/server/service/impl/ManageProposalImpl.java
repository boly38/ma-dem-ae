package net.mademocratie.gae.server.service.impl;

import java.util.Date;
import java.util.List;

import net.mademocratie.gae.model.Proposal;
import net.mademocratie.gae.server.service.IManageProposal;
import net.mademocratie.gae.server.service.IProposal;
import net.mademocratie.gae.server.service.IRepository;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.Inject;

public class ManageProposalImpl implements IManageProposal {
    // ~services
    @Inject
    private IProposal proposalsQueries;
    @Inject
    private IRepository<Proposal> proposalRepo;

    
	@Override
	public void addProposal(Proposal inputProposal) {
    	UserService userService = UserServiceFactory.getUserService();
    	User user = userService.getCurrentUser();                
    	inputProposal.setAuthor(user);
		inputProposal.setDate(new Date());
		inputProposal.setAuthor(user);
       	proposalRepo.persist(inputProposal);
		
	}

	@Override
	public List<Proposal> latest(int max) {
		return proposalsQueries.latest(max);
	}

}
