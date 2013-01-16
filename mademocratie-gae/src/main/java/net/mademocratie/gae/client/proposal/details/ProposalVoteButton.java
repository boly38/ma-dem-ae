package net.mademocratie.gae.client.proposal.details;

import net.mademocratie.gae.client.common.WicketUtils;
import net.mademocratie.gae.model.VoteKind;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.markup.html.AjaxLink;

/**
 * ProposalVoteButton
 *
 * class not selected :
 * btn btn-info
 *
 * class selected :
 * btn btn-warning
 *
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
public abstract class ProposalVoteButton extends AjaxLink {
    public ProposalVoteButton(String id, VoteKind voteKind) {
        super(id);
        switch (voteKind) {
            case PRO:
                setBody(WicketUtils.getStringResourceModel(this, "proposal.vote.pro.button"));
                add(new AttributeModifier("class","btn btn-info"));
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

    public void toggle(boolean choice) {
        if (choice) {
            add(new AttributeModifier("class", "btn btn-warning"));
            return;
        }
        add(new AttributeModifier("class", "btn btn-info"));
        return;
    }
}
