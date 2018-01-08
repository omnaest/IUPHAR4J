package org.omnaest.metabolics.iuphar.wrapper.domain2;

import org.omnaest.metabolics.iuphar.domain.DatabaseLink.Database;
import org.omnaest.metabolics.iuphar.domain.SpeciesType;

public interface InteractionAccessor
{
    public String getName();

    public String getHumanRelatedDatabaseId(Database database);

    public String getDatabaseId(Database database, SpeciesType speciesType);

    public String getGene(SpeciesType speciesType);

    public String getHumanGene();
}
