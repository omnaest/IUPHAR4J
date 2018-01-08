package org.omnaest.metabolics.iuphar.domain;

import org.omnaest.metabolics.iuphar.domain.raw.SpeciesType;
import org.omnaest.metabolics.iuphar.domain.raw.DatabaseLink.Database;

public interface InteractionAccessor
{
    public String getName();

    public String getHumanRelatedDatabaseId(Database database);

    public String getDatabaseId(Database database, SpeciesType speciesType);

    public String getGene(SpeciesType speciesType);

    public String getHumanGene();
}
