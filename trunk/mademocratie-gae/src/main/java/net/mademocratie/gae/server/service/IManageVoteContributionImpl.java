package net.mademocratie.gae.server.service;

import com.google.inject.ImplementedBy;
import net.mademocratie.gae.model.VoteContribution;
import net.mademocratie.gae.server.service.impl.ManageVoteContributionImpl;
import net.mademocratie.gae.server.service.impl.ManageVoteImpl;

import java.util.List;

@ImplementedBy(ManageVoteContributionImpl.class)
public interface IManageVoteContributionImpl {
    List<VoteContribution> latest(int maxContributions);
}
