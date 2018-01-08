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
package org.omnaest.metabolics.iuphar;

import org.omnaest.metabolics.iuphar.domain.raw.Comments;
import org.omnaest.metabolics.iuphar.domain.raw.DatabaseLinks;
import org.omnaest.metabolics.iuphar.domain.raw.Functions;
import org.omnaest.metabolics.iuphar.domain.raw.GeneProteinInformations;
import org.omnaest.metabolics.iuphar.domain.raw.Interactions;
import org.omnaest.metabolics.iuphar.domain.raw.InteractionsShort;
import org.omnaest.metabolics.iuphar.domain.raw.Ligand;
import org.omnaest.metabolics.iuphar.domain.raw.Ligands;
import org.omnaest.metabolics.iuphar.domain.raw.Synonyms;
import org.omnaest.metabolics.iuphar.domain.raw.Targets;
import org.omnaest.metabolics.iuphar.utils.JSONHelper;
import org.omnaest.metabolics.iuphar.utils.RestHelper;

public class IUPHARRestApiUtils
{
    private final static String BASE_URL = "http://www.guidetopharmacology.org/services";

    public static Targets getTargets()
    {
        return JSONHelper.readFromString(RestHelper.requestGet(BASE_URL + "/targets"), Targets.class);
    }

    public static Functions getTargetFunction(long targetId)
    {
        return JSONHelper.readFromString(RestHelper.requestGet(BASE_URL + "/targets/" + targetId + "/function"), Functions.class);
    }

    public static GeneProteinInformations getTargetGeneProteinInformation(long targetId)
    {
        return JSONHelper.readFromString(RestHelper.requestGet(BASE_URL + "/targets/" + targetId + "/geneProteinInformation"), GeneProteinInformations.class);
    }

    public static Synonyms getTargetSynonyms(long targetId)
    {
        return JSONHelper.readFromString(RestHelper.requestGet(BASE_URL + "/targets/" + targetId + "/synonyms"), Synonyms.class);
    }

    public static DatabaseLinks getTargetDatabaseLinks(long targetId)
    {
        return JSONHelper.readFromString(RestHelper.requestGet(BASE_URL + "/targets/" + targetId + "/databaseLinks"), DatabaseLinks.class);
    }

    public static Interactions getTargetInteractions(Long targetId)
    {
        return JSONHelper.readFromString(RestHelper.requestGet(BASE_URL + "/targets/" + targetId + "/interactions"), Interactions.class);
    }

    public static InteractionsShort getInteractions()
    {
        return JSONHelper.readFromString(RestHelper.requestGet(BASE_URL + "/interactions"), InteractionsShort.class);
    }

    public static Ligand getLigand(Long ligandId)
    {
        return JSONHelper.readFromString(RestHelper.requestGet(BASE_URL + "/ligands/" + ligandId), Ligand.class);
    }

    public static Synonyms getLigandSynonyms(long ligandId)
    {
        return JSONHelper.readFromString(RestHelper.requestGet(BASE_URL + "/ligands/" + ligandId + "/synonyms"), Synonyms.class);
    }

    public static DatabaseLinks getLigandDatabaseLinks(long ligandId)
    {
        return JSONHelper.readFromString(RestHelper.requestGet(BASE_URL + "/ligands/" + ligandId + "/databaseLinks"), DatabaseLinks.class);
    }

    public static Ligands getLigands()
    {
        return JSONHelper.readFromString(RestHelper.requestGet(BASE_URL + "/ligands"), Ligands.class);
    }

    public static Comments getLigandComments(Long ligandId)
    {
        return JSONHelper.readFromString(RestHelper.requestGet(BASE_URL + "/ligands/" + ligandId + "/comments"), Comments.class);
    }
}
