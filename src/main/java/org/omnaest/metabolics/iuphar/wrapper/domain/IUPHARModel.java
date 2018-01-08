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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.omnaest.metabolics.iuphar.domain.Comments;
import org.omnaest.metabolics.iuphar.domain.DatabaseLinks;
import org.omnaest.metabolics.iuphar.domain.Functions;
import org.omnaest.metabolics.iuphar.domain.GeneProteinInformations;
import org.omnaest.metabolics.iuphar.domain.InteractionsShort;
import org.omnaest.metabolics.iuphar.domain.Ligands;
import org.omnaest.metabolics.iuphar.domain.Synonyms;
import org.omnaest.metabolics.iuphar.domain.Targets;
import org.omnaest.metabolics.iuphar.utils.JSONHelper;

public class IUPHARModel
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

    public IUPHARModel()
    {
        super();
    }

    public IUPHARModel(Ligands ligands, Targets targets, InteractionsShort interactions)
    {
        super();
        this.ligands = ligands;
        this.targets = targets;
        this.interactions = interactions;
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
