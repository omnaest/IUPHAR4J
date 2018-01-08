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

import java.io.File;
import java.util.function.Consumer;

import org.junit.Ignore;
import org.junit.Test;
import org.omnaest.metabolics.iuphar.domain.raw.DatabaseLink.Database;
import org.omnaest.metabolics.iuphar.wrapper.IUpharModelManager;
import org.omnaest.metabolics.iuphar.wrapper.IUpharModelManagerLoader;

public class IUpharUtilsTest
{

    private File file = new File("data/model.json");

    @Test
    //    @Ignore
    public void testGetInstanceFromRestApi() throws Exception
    {
        IUpharModelManagerLoader modelManager = IUpharUtils.getInstance();

        modelManager.usingLocalCache()
                    .loadFromRestApi()
                    .saveToFile(this.file);

    }

    @Test
    @Ignore
    public void testGetInstanceFromFile() throws Exception
    {
        IUpharModelManager modelManager = IUpharUtils.getInstance()
                                                     .loadFromFile(this.file);
        //		System.out.println(modelManager	.findLigand("olanzapine")
        //										.findTargets()
        //										.get());

        modelManager.findLigandForMetabolite("histamine")
                    .findTargets()
                    .get()
                    .stream()
                    .map(interactionWithTarget -> interactionWithTarget.getTarget()
                                                                       .getName())
                    .forEach(this.listTargetAndItsLigands(modelManager));

    }

    @Test
    @Ignore
    public void testGetInstanceFromFile2() throws Exception
    {
        IUpharModelManager modelManager = IUpharUtils.getInstance()
                                                     .usingLocalCache()
                                                     .loadFromRestApi();

        modelManager.findLigandForMetabolite("histamine")
                    .findTargets()
                    .getInteractions()
                    .forEach(interaction ->
                    {
                        System.out.println(interaction.getName());
                        System.out.println(interaction.getHumanGene());
                        System.out.println(interaction.getHumanRelatedDatabaseId(Database.UNIPROT));
                    });

    }

    private Consumer<? super String> listTargetAndItsLigands(IUpharModelManager modelManager)
    {
        return targetName ->
        {
            System.out.println(targetName);

            modelManager.findTargetByName(targetName)
                        .findLigands()
                        .get()
                        .stream()
                        .sorted((i1, i2) -> i1.getInteraction()
                                              .getAffinity()
                                              .compareTo(i2.getInteraction()
                                                           .getAffinity()))
                        .forEach(interactionWithLigand -> System.out.println("  <-" + interactionWithLigand.getLigand()
                                                                                                           .getName()
                                + "(" + interactionWithLigand.getInteraction()
                                                             .getAffinity()
                                + " " + interactionWithLigand.getInteraction()
                                                             .getAffinityType()
                                + ")"));

        };
    }

}
