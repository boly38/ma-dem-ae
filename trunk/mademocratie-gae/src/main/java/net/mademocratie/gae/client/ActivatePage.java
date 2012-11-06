package net.mademocratie.gae.client;

import com.google.inject.Inject;
import net.mademocratie.gae.client.common.PageTemplate;
import net.mademocratie.gae.server.service.IManageCitizen;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.logging.Logger;

/**
 * ActivatePage
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
public class ActivatePage extends PageTemplate {
    private final static Logger LOGGER = Logger.getLogger(ActivatePage.class.getName());
    // ~services
    @Inject
    private IManageCitizen manageCitizen;
    public static final String PARAM_AK = "ak";
    public static final String PARAM_CID = "cid";

    /**
     * PageTemplate constructor
     *
     * @param params - page parameters map
     */
    public ActivatePage(final PageParameters params) {
        super(params);
        this.params = params;
        initComponents();
        setOutputMarkupId(true);
    }

    private void initComponents() {
        /**
        Citizen justRegisteredCitizen = manageCitizen.find(params.get(PARAM_CID).toString());
         */
    }
}

