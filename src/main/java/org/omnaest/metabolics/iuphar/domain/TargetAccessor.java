package org.omnaest.metabolics.iuphar.domain;

import java.util.stream.Stream;

import org.omnaest.metabolics.iuphar.domain.raw.DatabaseLink.Database;
import org.omnaest.metabolics.iuphar.domain.raw.SpeciesType;

public interface TargetAccessor
{
    public String getName();

    public String getHumanRelatedDatabaseId(Database database);

    public String getDatabaseId(Database database, SpeciesType speciesType);

    public String getGene(SpeciesType speciesType);

    public String getHumanGene();

    public Stream<LigandInteractionAccessor> getLigandInteractions();
}
