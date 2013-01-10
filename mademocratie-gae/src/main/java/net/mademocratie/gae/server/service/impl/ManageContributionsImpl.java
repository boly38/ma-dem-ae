package net.mademocratie.gae.server.service.impl;

import net.mademocratie.gae.model.IContribution;
import net.mademocratie.gae.model.Proposal;
import net.mademocratie.gae.model.Vote;
import net.mademocratie.gae.server.service.IManageContributions;
import net.mademocratie.gae.server.service.IManageProposal;
import net.mademocratie.gae.server.service.IManageVote;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * ManageContributionsImpl
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
public class ManageContributionsImpl implements IManageContributions {

    private final static Logger LOGGER = Logger.getLogger(ManageContributionsImpl.class.getName());

    //~services
    @Inject
    private IManageVote manageVote;

    @Inject
    private IManageProposal manageProposal;

    @Override
    public List<IContribution> getLastContributions(int maxContributions) {
        List<Proposal> latestProposals = manageProposal.latest(maxContributions);
        List<Vote> latestVotes = manageVote.latest(maxContributions);
        List<IContribution> latestContributions = new ArrayList<IContribution>();
        latestContributions.addAll(latestProposals);
        latestContributions.addAll(latestVotes);
        if (latestContributions.size() == 0) {
            return latestContributions;
        }
        Collections.sort(latestContributions, new ContributionDateComparator());
        Collections.reverse(latestContributions);
        int subListLastIndex = Math.min(latestContributions.size(), maxContributions);
        return latestContributions.subList(0, subListLastIndex);
    }

}
