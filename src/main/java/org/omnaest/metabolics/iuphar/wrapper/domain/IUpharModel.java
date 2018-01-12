/*

	Copyright 2017 Danny Kunz

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.


*/
package org.omnaest.metabolics.iuphar.wrapper.domain;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.omnaest.metabolics.iuphar.domain.raw.Comments;
import org.omnaest.metabolics.iuphar.domain.raw.DatabaseLinks;
import org.omnaest.metabolics.iuphar.domain.raw.Functions;
import org.omnaest.metabolics.iuphar.domain.raw.GeneProteinInformations;
import org.omnaest.metabolics.iuphar.domain.raw.InteractionShort;
import org.omnaest.metabolics.iuphar.domain.raw.InteractionsShort;
import org.omnaest.metabolics.iuphar.domain.raw.Ligand;
import org.omnaest.metabolics.iuphar.domain.raw.Ligands;
import org.omnaest.metabolics.iuphar.domain.raw.Synonyms;
import org.omnaest.metabolics.iuphar.domain.raw.Target;
import org.omnaest.metabolics.iuphar.domain.raw.Targets;
import org.omnaest.metabolics.iuphar.utils.JSONHelper;
import org.omnaest.utils.element.cached.CachedElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class IUpharModel
{
    private Ligands           ligands;
    private Targets           targets;
    private InteractionsShort interactions;

    private Map<Long, Functions>               targetIdToFunctionsMap              = new ConcurrentHashMap<>();
    private Map<Long, Synonyms>                targetIdToSynonymsMap               = new ConcurrentHashMap<>();
    private Map<Long, DatabaseLinks>           targetIdToDatabaseLinksMap          = new ConcurrentHashMap<>();
    private Map<Long, GeneProteinInformations> targetIdToGeneProteinInformationMap = new ConcurrentHashMap<>();

    private Map<Long, Synonyms>      ligandIdToSynonymsMap      = new ConcurrentHashMap<>();
    private Map<Long, DatabaseLinks> ligandIdToDatabaseLinksMap = new ConcurrentHashMap<>();
    private Map<Long, Comments>      ligandIdToCommentsMap      = new ConcurrentHashMap<>();

    @JsonIgnore
    private CachedElement<Map<Long, List<InteractionShort>>> targetIdToInteraction = CachedElement.of(() -> this.interactions.stream()
                                                                                                                             .filter(interaction -> interaction.getTargetId() != null)
                                                                                                                             .collect(Collectors.groupingBy(interaction -> interaction.getTargetId())));
    @JsonIgnore
    private CachedElement<Map<Long, List<InteractionShort>>> ligandIdToInteraction = CachedElement.of(() -> this.interactions.stream()
                                                                                                                             .filter(interaction -> interaction.getLigandId() != null)
                                                                                                                             .collect(Collectors.groupingBy(interaction -> interaction.getLigandId())));

    @JsonIgnore
    private CachedElement<Map<Long, Ligand>> ligandIdToLigand = CachedElement.of(() -> this.ligands.stream()
                                                                                                   .filter(ligand -> ligand.getLigandId() != null)
                                                                                                   .collect(Collectors.toMap(ligand -> ligand.getLigandId(),
                                                                                                                             ligand -> ligand)));

    @JsonIgnore
    private CachedElement<Map<Long, Target>> targetIdToTarget = CachedElement.of(() -> this.targets.stream()
                                                                                                   .filter(target -> target.getTargetId() != null)
                                                                                                   .collect(Collectors.toMap(target -> target.getTargetId(),
                                                                                                                             target -> target)));

    public IUpharModel()
    {
        super();
    }

    public IUpharModel(Ligands ligands, Targets targets, InteractionsShort interactions)
    {
        super();
        this.ligands = ligands;
        this.targets = targets;
        this.interactions = interactions;
    }

    public Map<Long, List<InteractionShort>> getLigandIdToInteraction()
    {
        return this.ligandIdToInteraction.get();
    }

    public Map<Long, List<InteractionShort>> getTargetIdToInteraction()
    {
        return this.targetIdToInteraction.get();
    }

    public Map<Long, Ligand> getLigandIdToLigand()
    {
        return this.ligandIdToLigand.get();
    }

    public Map<Long, Target> getTargetIdToTarget()
    {
        return this.targetIdToTarget.get();
    }

    public Ligands getLigands()
    {
        return this.ligands;
    }

    public Targets getTargets()
    {
        return this.targets;
    }

    public InteractionsShort getInteractions()
    {
        return this.interactions;
    }

    public Map<Long, Functions> getTargetIdToFunctionsMap()
    {
        return this.targetIdToFunctionsMap;
    }

    public Map<Long, Synonyms> getTargetIdToSynonymsMap()
    {
        return this.targetIdToSynonymsMap;
    }

    public Map<Long, DatabaseLinks> getTargetIdToDatabaseLinksMap()
    {
        return this.targetIdToDatabaseLinksMap;
    }

    public Map<Long, Synonyms> getLigandIdToSynonymsMap()
    {
        return this.ligandIdToSynonymsMap;
    }

    public Map<Long, DatabaseLinks> getLigandIdToDatabaseLinksMap()
    {
        return this.ligandIdToDatabaseLinksMap;
    }

    public Map<Long, Comments> getLigandIdToCommentsMap()
    {
        return this.ligandIdToCommentsMap;
    }

    public Map<Long, GeneProteinInformations> getTargetIdToGeneProteinInformationMap()
    {
        return this.targetIdToGeneProteinInformationMap;
    }

    @Override
    public String toString()
    {
        return JSONHelper.prettyPrint(this);
    }

}
