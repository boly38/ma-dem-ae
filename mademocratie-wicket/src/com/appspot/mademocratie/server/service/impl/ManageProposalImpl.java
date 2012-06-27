package com.appspot.mademocratie.server.service.impl;

import java.util.Date;
import java.util.List;

import com.appspot.mademocratie.model.Proposal;
import com.appspot.mademocratie.server.service.IManageProposal;
import com.appspot.mademocratie.server.service.IProposal;
import com.appspot.mademocratie.server.service.IRepository;
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
