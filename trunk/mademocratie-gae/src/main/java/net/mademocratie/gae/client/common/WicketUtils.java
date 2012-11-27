package net.mademocratie.gae.client.common;

import org.apache.wicket.Component;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

/**
 * WicketUtils
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
public class WicketUtils {
    /**
     * fix Localizer warning : cf. https://issues.apache.org/jira/browse/WICKET-990
     *  Tried to retrieve a localized string for a component that has not yet been added to the page.
     * @param key label property key
     * @return string resource model
     */
    public static StringResourceModel getStringResourceModel(Component component, java.lang.String key) {
        return new StringResourceModel(key, component, null);
    }

    public static StringResourceModel getStringResourceModel(Component component, java.lang.String key, Model model) {
        return new StringResourceModel(key, component, model);
    }
}
