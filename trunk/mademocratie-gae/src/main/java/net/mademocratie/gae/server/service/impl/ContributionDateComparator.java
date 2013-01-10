package net.mademocratie.gae.server.service.impl;

import net.mademocratie.gae.model.IContribution;

import java.util.Comparator;
import java.util.Date;

/**
 * ContributionDateComparator
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
public class ContributionDateComparator implements Comparator<IContribution> {
    @Override
    public int compare(IContribution o1, IContribution o2) {
        Date age1 = o1.getDate();
        Date age2 = o2.getDate();
        return age1.compareTo(age2);
    }
}
