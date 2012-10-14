package net.mademocratie.gae.client;/**
 * Created with IntelliJ IDEA.
 * User: bricesteph
 * Date: 14/10/12
 * Time: 14:45
 * To change this template use File | Settings | File Templates.
 */

import net.mademocratie.gae.client.common.PageTemplate;

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
        getSession().invalidate();
    }
}
