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
package org.omnaest.metabolomics.iuphar;

import org.junit.Ignore;
import org.junit.Test;
import org.omnaest.metabolomics.iuphar.IUpharRESTUtils;
import org.omnaest.metabolomics.iuphar.domain.raw.DatabaseLinks;
import org.omnaest.metabolomics.iuphar.domain.raw.Functions;
import org.omnaest.metabolomics.iuphar.domain.raw.GeneProteinInformations;
import org.omnaest.metabolomics.iuphar.domain.raw.Interactions;
import org.omnaest.metabolomics.iuphar.domain.raw.InteractionsShort;
import org.omnaest.metabolomics.iuphar.domain.raw.Ligand;
import org.omnaest.metabolomics.iuphar.domain.raw.Ligands;
import org.omnaest.metabolomics.iuphar.domain.raw.Synonyms;
import org.omnaest.metabolomics.iuphar.domain.raw.Targets;
import org.omnaest.utils.JSONHelper;

public class IUpharRESTUtilsTest
{

    @Test
    @Ignore
    public void testGetTargets() throws Exception
    {
        Targets targets = IUpharRESTUtils.getInstance()
                                         .getTargets();
        System.out.println(JSONHelper.prettyPrint(targets));
    }

    @Test
    @Ignore
    public void testGetTargetInteractions() throws Exception
    {
        Interactions interactions = IUpharRESTUtils.getInstance()
                                                   .getTargetInteractions(262l);
        System.out.println(JSONHelper.prettyPrint(interactions));
    }

    @Test
    @Ignore
    public void testGetLigand() throws Exception
    {
        Ligand ligand = IUpharRESTUtils.getInstance()
                                       .getLigand(8299l);
        System.out.println(JSONHelper.prettyPrint(ligand));
    }

    @Test
    @Ignore
    public void testGetLigands() throws Exception
    {
        Ligands ligands = IUpharRESTUtils.getInstance()
                                         .getLigands();
        System.out.println(JSONHelper.prettyPrint(ligands));
    }

    @Test
    @Ignore
    public void testGetInteractions() throws Exception
    {
        InteractionsShort interactions = IUpharRESTUtils.getInstance()
                                                        .getInteractions();
        System.out.println(JSONHelper.prettyPrint(interactions));
    }

    @Test
    @Ignore
    public void testGetTargetFunction() throws Exception
    {
        Functions functions = IUpharRESTUtils.getInstance()
                                             .getTargetFunction(262l);
        System.out.println(JSONHelper.prettyPrint(functions));
    }

    @Test
    @Ignore
    public void testGetTargetSynonyms() throws Exception
    {
        Synonyms synonyms = IUpharRESTUtils.getInstance()
                                           .getTargetSynonyms(262l);
        System.out.println(JSONHelper.prettyPrint(synonyms));
    }

    @Test
    @Ignore
    public void testGetTargetDatabaseLinks() throws Exception
    {
        DatabaseLinks links = IUpharRESTUtils.getInstance()
                                             .getTargetDatabaseLinks(262l);
        System.out.println(JSONHelper.prettyPrint(links));
    }

    @Test
    @Ignore
    public void testGetLigandSynonyms() throws Exception
    {
        Synonyms synonyms = IUpharRESTUtils.getInstance()
                                           .getLigandSynonyms(47l);
        System.out.println(JSONHelper.prettyPrint(synonyms));
    }

    @Test
    @Ignore
    public void testGetLigandDatabaseLinks() throws Exception
    {
        DatabaseLinks links = IUpharRESTUtils.getInstance()
                                             .getLigandDatabaseLinks(47l);
        System.out.println(JSONHelper.prettyPrint(links));
    }

    @Test
    public void testGetTargetGeneProteinInformation() throws Exception
    {
        GeneProteinInformations geneProteinInformation = IUpharRESTUtils.getInstance()
                                                                        .getTargetGeneProteinInformation(1);
        System.out.println(JSONHelper.prettyPrint(geneProteinInformation));
    }

}
