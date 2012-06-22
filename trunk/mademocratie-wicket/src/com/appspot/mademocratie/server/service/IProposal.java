package com.appspot.mademocratie.server.service;

import java.util.List;

import com.appspot.mademocratie.model.Proposal;

public interface IProposal {
    /**
     * Return the latest proposals, ordered by descending date.
     *
     * @param max the maximum number of proposals to return
     * @return the proposals
     */
    List<Proposal> latest(int max);
}
