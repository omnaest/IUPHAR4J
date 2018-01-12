package org.omnaest.metabolics.iuphar.domain;

import java.util.stream.Stream;

import org.omnaest.metabolics.iuphar.domain.raw.LigandType;

public interface LigandAccessor
{
    public String getName();

    public String getNameHtmlStripped();

    public LigandType getType();

    public boolean hasType(LigandType ligandType);

    public Stream<TargetInteractionAccessor> getTargetInteractions();

    public String getClinicalUse();

    public long getLigandId();

}
