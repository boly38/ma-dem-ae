package net.mademocratie.gae.server.service;

import com.google.inject.ImplementedBy;
import net.mademocratie.gae.model.IContribution;
import net.mademocratie.gae.server.service.impl.ManageContributionsImpl;

import java.util.List;

@ImplementedBy(ManageContributionsImpl.class)
public interface IManageContributions {
    List<IContribution> getLastContributions(int maxContributions);
}
