package net.mademocratie.gae.server.service.impl.it;

import net.mademocratie.gae.server.GuiceModule;
import net.mademocratie.gae.server.service.impl.ManageVoteImpl;
import net.mademocratie.gae.test.GuiceJUnitRunner;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

/**
 * ManageVoteImplIT
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({ GuiceModule.class })
public class ManageVoteImplIT extends BaseIT {
    private static final Logger logger = Logger.getLogger(ManageVoteImplIT.class.getName());
    private ManageVoteImpl manageVote;

    /*** TODO (@DevInProgress) ***/

}
