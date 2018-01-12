package org.omnaest.metabolics.iuphar.domain.raw;

import java.util.Arrays;

public enum LigandType
{
    METABOLITE("Metabolite"), SYNTHETIC("Synthetic organic");

    private String type;

    private LigandType(String type)
    {
        this.type = type;
    }

    public static LigandType fromType(String type)
    {
        return Arrays.asList(values())
                     .stream()
                     .filter(value -> value.type.equalsIgnoreCase(type))
                     .findFirst()
                     .orElseGet(() -> null);
    }
}