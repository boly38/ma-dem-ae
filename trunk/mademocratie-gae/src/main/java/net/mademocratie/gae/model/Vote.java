package net.mademocratie.gae.model;

import javax.jdo.annotations.*;
import java.util.Date;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Vote {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private Date when;

    @Persistent
    private String citizenEmail = null;

    @Persistent
    private Long proposalId;

    @Persistent()
    private VoteKind kind;

    public Vote(String citizenEmail, VoteKind kind, Long proposalId) {
        this.citizenEmail = citizenEmail;
        this.kind = kind;
        this.proposalId = proposalId;
        this.when = new Date();
    }

    public String getCitizenEmail() {
        return citizenEmail;
    }

    public void setCitizenEmail(String citizenEmail) {
        this.citizenEmail = citizenEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getWhen() {
        return when;
    }

    public void setWhen(Date when) {
        this.when = when;
    }

    public VoteKind getKind() {
        return kind;
    }

    public void setKind(VoteKind kind) {
        this.kind = kind;
    }

    public Long getProposalId() {
        return proposalId;
    }

    public void setProposalId(Long proposalId) {
        this.proposalId = proposalId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[Vote");
        if (getId() != null) {
            sb.append("#").append(getId());
        }
        sb.append("|").append(getKind()).append("]")
                .append(" by ").append(getCitizenEmail())
                .append(" (proposal#").append(getProposalId())
                .append(") date=").append(getWhen());
        return sb.toString();
    }
}
