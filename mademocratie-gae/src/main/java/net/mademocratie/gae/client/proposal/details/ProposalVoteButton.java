package net.mademocratie.gae.client.proposal.details;

import net.mademocratie.gae.client.common.WicketUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.link.ExternalLink;

/**
 * ProposalVoteButton
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
public class ProposalVoteButton extends ExternalLink {
    public ProposalVoteButton(String id, String href, VoteKind voteKind) {
        super(id, href);
        switch (voteKind) {
            case PRO:
                setBody(WicketUtils.getStringResourceModel(this, "proposal.vote.pro.button"));
                add(new AttributeModifier("class", "btn btn-warning"));
                break;
            case NEUTRAL:
                setBody(WicketUtils.getStringResourceModel(this, "proposal.vote.neutral.button"));
                add(new AttributeModifier("class","btn btn-info"));
                break;
            case CON:
            default:
                setBody(WicketUtils.getStringResourceModel(this, "proposal.vote.con.button"));
                add(new AttributeModifier("class","btn btn-info"));
                break;
        }
    }
}
