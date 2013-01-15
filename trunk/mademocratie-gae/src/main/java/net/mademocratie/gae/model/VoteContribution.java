package net.mademocratie.gae.model;

import net.mademocratie.gae.client.proposal.ProposalPage;
import org.apache.wicket.Page;

public class VoteContribution extends Vote implements IContribution{

    private Citizen citizen;
    private Proposal proposal;

    public VoteContribution(Citizen citizen, Proposal proposal, Vote vote) {
        super(citizen != null ? citizen.getEmail() : null, proposal.getId(), vote.getKind());
        setId(vote.getId());
        setDate(vote.getDate());
        this.citizen = citizen;
        this.proposal = proposal;
    }

    @Override
    public Class<? extends Page> getContributionPage() {
        return ProposalPage.class;
    }

    @Override
    public String getContributionDetails() {
        return "vote on proposal '" + proposal.getTitle() + "'";
    }

    @Override
    public String toString() {
        return "contribution [vote " + super.toString() + " on " + (proposal != null ? proposal.toString() : "null proposal") + "]";
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public Proposal getProposal() {
        return proposal;
    }
}
