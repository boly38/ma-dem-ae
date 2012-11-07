package net.mademocratie.gae.client;

import com.google.inject.Inject;
import net.mademocratie.gae.client.common.PageTemplate;
import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.server.service.IManageCitizen;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
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
        activationChecks();
    }

    private void initComponents() {
        // Create feedback panel and add to page
        add(new FeedbackPanel("feedback"));

    }
    private void activationChecks() {
        Long cId = params.get(PARAM_CID).toLong();
        String activationKey = params.get(PARAM_AK).toString();
        Citizen justRegisteredCitizen = manageCitizen.getById(cId);
        if (justRegisteredCitizen == null
         || !activationKey.equals(justRegisteredCitizen.getCitizenStateData())) {
            error("Your activation link is deprecated, please login or register.");
        } else {
            success("Your the winner");
        }
    }
}

