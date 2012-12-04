package net.mademocratie.gae.client;

import net.mademocratie.gae.client.common.PageTemplate;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.logging.Logger;

/**
 * Generic exception handling.
 *  This page is called by exceptions :
 *  - pages exceptions
 *  - ExecutionHandlerRequestCycle::onRuntimeException
 */
public class ExceptionPage extends PageTemplate {
    private final static Logger LOGGER = Logger.getLogger(ExceptionPage.class.getName());
    /**
     * Constructor
     * @param pageParameters
     */
    public ExceptionPage(PageParameters pageParameters) {
        super(pageParameters);
        String exMessage = (String) pageParameters.get("message").toString();
        LOGGER.info("ExceptionPage " + exMessage);
        init(pageParameters, exMessage);
    }

    /**
     * Constructor
     * @param pageParameters
     * @param e
     */
    public ExceptionPage(PageParameters pageParameters, RuntimeException e) {
        super(pageParameters);
        LOGGER.info("ExceptionPage : "+ e.getMessage());
        init(pageParameters, e.getMessage());
    }

    @Override
    protected void setPageTitle() {
        add(new Label("head_page_title", getString("portal.error.title")));
    }

    private void init(PageParameters pageParameters, String message) {
        add(new Label("message", getString("portal.error.message")));
        add(new Label("description", message));
    }
}