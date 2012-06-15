package com.appspot.mademea.client.data;

import com.smartgwt.client.widgets.grid.ListGridRecord;


public class ProposalRecord extends ListGridRecord {
	  
    public ProposalRecord() {  
    }  
  
    public ProposalRecord(String proposal, String who, String when) {  
        setProposal(proposal);  
        setWho(who);  
        setWhen(when);  
    }  
  
    public void setProposal(String proposal) {  
        setAttribute("proposal", proposal);  
    }  
  
    public String getProposal() {  
        return getAttributeAsString("proposal");  
    }  
  
    public void setWho(String who) {  
        setAttribute("who", who);  
    }  
  
    public String getWho() {  
        return getAttributeAsString("who");  
    }  
  
    public void setWhen(String when) {  
        setAttribute("when", when);  
    }  
  
    public String getWhen() {  
        return getAttributeAsString("when");  
    }  
  }
