package net.mademocratie.gae.server.service;

import net.mademocratie.gae.model.Proposal;

import java.util.List;

public interface IProposal {
    /**
     * Return the latest proposals, ordered by descending date.
     *
     * @param max the maximum number of proposals to return
     * @return the proposals
     */
    List<Proposal> latest(int max);

    /**
     * remove all proposals
     */
    void removeAll();
}
