package net.mademocratie.gae.client;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import net.mademocratie.gae.client.common.PageTemplate;
import com.google.appengine.api.utils.SystemProperty;

public class AboutPage extends PageTemplate {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4044609068740426635L;

	public AboutPage(PageParameters params) {
		super(params);
        initComponents();		
	}
	
    private void initComponents() {
	    add(new Label("app-version", SystemProperty.applicationVersion.get() + "engine:" + SystemProperty.version.get()));
    }
}
