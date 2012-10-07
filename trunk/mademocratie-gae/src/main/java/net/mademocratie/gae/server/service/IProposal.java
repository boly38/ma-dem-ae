package net.mademocratie.gae.server.service;

import java.util.List;

import net.mademocratie.gae.model.Proposal;

public interface IProposal {
    /**
     * Return the latest proposals, ordered by descending date.
     *
     * @param max the maximum number of proposals to return
     * @return the proposals
     */
    List<Proposal> latest(int max);
}
