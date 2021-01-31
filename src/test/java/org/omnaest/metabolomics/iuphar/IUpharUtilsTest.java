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

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;
import org.omnaest.metabolomics.iuphar.IUpharUtils;
import org.omnaest.metabolomics.iuphar.domain.IUpharModelManager;
import org.omnaest.metabolomics.iuphar.domain.IUpharModelManagerLoader;
import org.omnaest.metabolomics.iuphar.domain.TargetAccessor;
import org.omnaest.metabolomics.iuphar.domain.raw.DatabaseLink.Database;

public class IUpharUtilsTest
{

    private File file = new File("data/model.json");

    @Test
    @Ignore
    public void testGetInstanceFromRestApi() throws Exception
    {
        IUpharModelManagerLoader modelManager = IUpharUtils.getInstance();

        modelManager.usingLocalCache()
                    .loadFromRestApi()
                    .saveToFile(this.file);

    }

    @Test
    @Ignore
    public void testGetInstanceFromFile2() throws Exception
    {
        IUpharModelManager modelManager = IUpharUtils.getInstance()
                                                     .loadFromFile(this.file);

        modelManager.findLigandForMetabolite("histamine")
                    .findFirst()
                    .get()
                    .getTargetInteractions()
                    .forEach(interaction ->
                    {
                        TargetAccessor target = interaction.getTarget();
                        System.out.println(target.getName());
                        System.out.println("  " + target.getHumanGene());
                        System.out.println("  " + target.getHumanRelatedDatabaseId(Database.UNIPROT));

                        target.getLigandInteractions()
                              .forEach(ligandInteraction ->
                              {
                                  System.out.println("   " + ligandInteraction.getLigand()
                                                                              .getName());
                                  System.out.println("    " + ligandInteraction.getProperties()
                                                                               .getAffinityPKi());
                                  System.out.println("    " + ligandInteraction.getProperties()
                                                                               .getActionType());

                              });
                    });

    }

}
