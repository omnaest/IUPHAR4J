package org.omnaest.metabolomics.iuphar.domain;

import java.util.stream.Stream;

import org.omnaest.metabolomics.iuphar.domain.raw.SpeciesType;
import org.omnaest.metabolomics.iuphar.domain.raw.DatabaseLink.Database;

public interface TargetAccessor
{
    public String getName();

    public String getHumanRelatedDatabaseId(Database database);

    public String getDatabaseId(Database database, SpeciesType speciesType);

    public String getGene(SpeciesType speciesType);

    public String getHumanGene();

    public Stream<LigandInteractionAccessor> getLigandInteractions();
}
