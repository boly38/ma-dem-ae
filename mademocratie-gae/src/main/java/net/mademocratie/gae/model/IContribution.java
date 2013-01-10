package net.mademocratie.gae.model;

import org.apache.wicket.Page;

import java.util.Date;

/**
 * IContribution
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
public interface IContribution {
    public Date getDate();
    public Class<? extends Page> getContributionPage();
    public String getContributionDetails();
}
