package org.omnaest.metabolics.iuphar.domain;

import org.omnaest.metabolics.iuphar.domain.raw.ActionType;

public interface InteractionPropertiesAccessor
{

    /**
     * Returns the affinity of type pKi or null if not available
     * 
     * @return
     */
    public Double getAffinityPKi();

    public String getAffinity();

    public String getAffinityType();

    /**
     * Returns the {@link ActionType} like {@link ActionType#AGONIST}
     * 
     * @return
     */
    public ActionType getActionType();
}
