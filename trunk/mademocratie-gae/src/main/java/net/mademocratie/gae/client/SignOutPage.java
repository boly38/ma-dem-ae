package net.mademocratie.gae.client;

import net.mademocratie.gae.client.common.PageTemplate;
import net.mademocratie.gae.server.CitizenSession;

/**
 * SignOutPage
 * <p/>
 * Last update  : $
 * Last author  : $
 *
 * @version : $
 */
public class SignOutPage extends PageTemplate {
    /**
     * Constructor
     */
    public SignOutPage() {
        super(null);
        CitizenSession.get().invalidateNow();
        setResponsePage(getApplication().getHomePage());
        setResponseNoCache();
    }
}
