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

import org.junit.Ignore;
import org.junit.Test;
import org.omnaest.metabolics.iuphar.domain.DatabaseLinks;
import org.omnaest.metabolics.iuphar.domain.Functions;
import org.omnaest.metabolics.iuphar.domain.Interactions;
import org.omnaest.metabolics.iuphar.domain.InteractionsShort;
import org.omnaest.metabolics.iuphar.domain.Ligand;
import org.omnaest.metabolics.iuphar.domain.Ligands;
import org.omnaest.metabolics.iuphar.domain.Synonyms;
import org.omnaest.metabolics.iuphar.domain.Targets;
import org.omnaest.metabolics.iuphar.utils.JSONHelper;

public class IUPHARRestApiUtilsTest
{

	@Test
	@Ignore
	public void testGetTargets() throws Exception
	{
		Targets targets = IUPHARRestApiUtils.getTargets();
		System.out.println(JSONHelper.prettyPrint(targets));
	}

	@Test
	@Ignore
	public void testGetTargetInteractions() throws Exception
	{
		Interactions interactions = IUPHARRestApiUtils.getTargetInteractions(262l);
		System.out.println(JSONHelper.prettyPrint(interactions));
	}

	@Test
	@Ignore
	public void testGetLigand() throws Exception
	{
		Ligand ligand = IUPHARRestApiUtils.getLigand(8299l);
		System.out.println(JSONHelper.prettyPrint(ligand));
	}

	@Test
	@Ignore
	public void testGetLigands() throws Exception
	{
		Ligands ligands = IUPHARRestApiUtils.getLigands();
		System.out.println(JSONHelper.prettyPrint(ligands));
	}

	@Test
	@Ignore
	public void testGetInteractions() throws Exception
	{
		InteractionsShort interactions = IUPHARRestApiUtils.getInteractions();
		System.out.println(JSONHelper.prettyPrint(interactions));
	}

	@Test
	@Ignore
	public void testGetTargetFunction() throws Exception
	{
		Functions functions = IUPHARRestApiUtils.getTargetFunction(262l);
		System.out.println(JSONHelper.prettyPrint(functions));
	}

	@Test
	@Ignore
	public void testGetTargetSynonyms() throws Exception
	{
		Synonyms synonyms = IUPHARRestApiUtils.getTargetSynonyms(262l);
		System.out.println(JSONHelper.prettyPrint(synonyms));
	}

	@Test
	@Ignore
	public void testGetTargetDatabaseLinks() throws Exception
	{
		DatabaseLinks links = IUPHARRestApiUtils.getTargetDatabaseLinks(262l);
		System.out.println(JSONHelper.prettyPrint(links));
	}

	@Test
	@Ignore
	public void testGetLigandSynonyms() throws Exception
	{
		Synonyms synonyms = IUPHARRestApiUtils.getLigandSynonyms(47l);
		System.out.println(JSONHelper.prettyPrint(synonyms));
	}

	@Test
	public void testGetLigandDatabaseLinks() throws Exception
	{
		DatabaseLinks links = IUPHARRestApiUtils.getLigandDatabaseLinks(47l);
		System.out.println(JSONHelper.prettyPrint(links));
	}

}
