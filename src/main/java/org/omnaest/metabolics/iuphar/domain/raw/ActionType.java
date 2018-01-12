package org.omnaest.metabolics.iuphar.domain.raw;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

public enum ActionType
{
    AGONIST, ANTAGONIST;

    public static ActionType matching(String type)
    {
        return Arrays.asList(values())
                     .stream()
                     .filter(actionType -> StringUtils.equalsIgnoreCase(actionType.name(), type))
                     .findFirst()
                     .orElse(null);
    }
}