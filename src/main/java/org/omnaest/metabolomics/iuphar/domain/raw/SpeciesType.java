package org.omnaest.metabolomics.iuphar.domain.raw;

import org.apache.commons.lang.StringUtils;

public enum SpeciesType
{
    HUMAN, RAT, MOUSE;

    public boolean matches(String species)
    {
        return StringUtils.equalsIgnoreCase(species, this.name());
    }
}