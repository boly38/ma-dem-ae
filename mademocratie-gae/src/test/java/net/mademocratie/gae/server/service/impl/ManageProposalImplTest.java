package net.mademocratie.gae.server.service.impl;

import com.google.inject.Inject;
import net.mademocratie.gae.model.Proposal;
import net.mademocratie.gae.server.service.IManageProposal;
import org.junit.Test;

/**
 * @DevInProgress
 */
public class ManageProposalImplTest {
    @Inject
    private IManageProposal manageProposal;

    @Test
    public void testAddProposal() throws Exception {
        Proposal testProposal = new Proposal("test", "test");
        manageProposal.addProposal(testProposal);
    }

    @Test
    public void testLatest() throws Exception {

    }
}
