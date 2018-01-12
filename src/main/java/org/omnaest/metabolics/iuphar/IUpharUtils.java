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
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.omnaest.metabolics.iuphar.IUpharRESTUtils.IUpharRestAccessor;
import org.omnaest.metabolics.iuphar.domain.InteractionPropertiesAccessor;
import org.omnaest.metabolics.iuphar.domain.LigandAccessor;
import org.omnaest.metabolics.iuphar.domain.LigandInteractionAccessor;
import org.omnaest.metabolics.iuphar.domain.TargetAccessor;
import org.omnaest.metabolics.iuphar.domain.TargetInteractionAccessor;
import org.omnaest.metabolics.iuphar.domain.raw.ActionType;
import org.omnaest.metabolics.iuphar.domain.raw.Comments;
import org.omnaest.metabolics.iuphar.domain.raw.DatabaseLink;
import org.omnaest.metabolics.iuphar.domain.raw.DatabaseLink.Database;
import org.omnaest.metabolics.iuphar.domain.raw.DatabaseLinks;
import org.omnaest.metabolics.iuphar.domain.raw.GeneProteinInformation;
import org.omnaest.metabolics.iuphar.domain.raw.GeneProteinInformations;
import org.omnaest.metabolics.iuphar.domain.raw.InteractionShort;
import org.omnaest.metabolics.iuphar.domain.raw.InteractionsShort;
import org.omnaest.metabolics.iuphar.domain.raw.Ligand;
import org.omnaest.metabolics.iuphar.domain.raw.LigandType;
import org.omnaest.metabolics.iuphar.domain.raw.Ligands;
import org.omnaest.metabolics.iuphar.domain.raw.SpeciesType;
import org.omnaest.metabolics.iuphar.domain.raw.Synonyms;
import org.omnaest.metabolics.iuphar.domain.raw.Target;
import org.omnaest.metabolics.iuphar.domain.raw.Targets;
import org.omnaest.metabolics.iuphar.utils.IdAndFutureValue;
import org.omnaest.metabolics.iuphar.utils.JSONHelper;
import org.omnaest.metabolics.iuphar.wrapper.IUpharModelManager;
import org.omnaest.metabolics.iuphar.wrapper.IUpharModelManagerLoader;
import org.omnaest.metabolics.iuphar.wrapper.InteractionsWithLigandsManager;
import org.omnaest.metabolics.iuphar.wrapper.InteractionsWithTargetsManager;
import org.omnaest.metabolics.iuphar.wrapper.LigandManager;
import org.omnaest.metabolics.iuphar.wrapper.TargetManager;
import org.omnaest.metabolics.iuphar.wrapper.domain.IUpharModel;
import org.omnaest.metabolics.iuphar.wrapper.domain.InteractionWithLigand;
import org.omnaest.metabolics.iuphar.wrapper.domain.InteractionWithTarget;
import org.omnaest.metabolics.iuphar.wrapper.domain.InteractionsWithLigands;
import org.omnaest.metabolics.iuphar.wrapper.domain.InteractionsWithTargets;
import org.omnaest.utils.NumberUtils;
import org.omnaest.utils.cache.Cache;
import org.omnaest.utils.cache.JsonFolderFilesCache;
import org.omnaest.utils.rest.client.RestClient.Proxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IUpharUtils
{
    private final static Logger LOG = LoggerFactory.getLogger(IUpharUtils.class);

    private static class IUpharModelManagerImpl implements IUpharModelManagerLoader, IUpharModelManager
    {
        private static class InteractionPropertiesAccessorImpl implements InteractionPropertiesAccessor
        {
            private final InteractionShort interaction;

            private InteractionPropertiesAccessorImpl(InteractionShort interaction)
            {
                this.interaction = interaction;
            }

            @Override
            public String getAffinity()
            {
                return this.interaction.getAffinity();
            }

            @Override
            public String getAffinityType()
            {
                return this.interaction.getAffinityType();
            }

            @Override
            public ActionType getActionType()
            {
                return this.interaction.getTypeAsEnum();
            }

            @Override
            public Double getAffinityPKi()
            {
                Double retval = null;

                if (StringUtils.isNotBlank(this.getAffinity()) && StringUtils.equalsIgnoreCase(StringUtils.trim(this.getAffinityType()), "pKi"))
                {
                    retval = NumberUtils.toDouble(this.getAffinity()
                                                      .split("-")[0].trim());
                    retval = retval < 0.0001 ? null : retval;
                }

                return retval;
            }
        }

        private IUpharModel        iupharModel;
        private IUpharRestAccessor restAccessor = IUpharRESTUtils.getInstance();

        @Override
        public IUpharModelManagerImpl usingProxy(Proxy proxy)
        {
            this.restAccessor.withProxy(proxy);
            return this;
        }

        @Override
        public IUpharModelManagerImpl usingCache(Cache cache)
        {
            this.restAccessor.usingCache(cache);
            return this;
        }

        @Override
        public IUpharModelManagerImpl usingLocalCache()
        {
            return this.usingCache(new JsonFolderFilesCache(new File("cache/iuphar")));
        }

        @Override
        public IUpharModelManager loadFromRestApi()
        {
            Ligands ligands = this.restAccessor.getLigands();
            Targets targets = this.restAccessor.getTargets();
            InteractionsShort interactions = this.restAccessor.getInteractions();

            this.iupharModel = new IUpharModel(ligands, targets, interactions);

            try
            {
                ExecutorService executorService = Executors.newFixedThreadPool(4);

                this.iupharModel.getLigandIdToSynonymsMap()
                                .putAll(ligands.stream()
                                               .map(ligand -> ligand.getLigandId())
                                               .map(ligandId -> new IdAndFutureValue<>(ligandId,
                                                                                       executorService.submit(() -> this.restAccessor.getLigandSynonyms(ligandId))))
                                               .filter(ligandIdAndValue -> ligandIdAndValue.getValue() != null)
                                               .collect(Collectors.toMap(ligandIdAndValue -> ligandIdAndValue.getId(),
                                                                         ligandIdAndValue -> ligandIdAndValue.getValue())));

                this.iupharModel.getLigandIdToCommentsMap()
                                .putAll(ligands.stream()
                                               .map(ligand -> ligand.getLigandId())
                                               .map(ligandId -> new IdAndFutureValue<>(ligandId,
                                                                                       executorService.submit(() -> this.restAccessor.getLigandComments(ligandId))))
                                               .filter(ligandIdAndValue -> ligandIdAndValue.getValue() != null)
                                               .collect(Collectors.toMap(ligandIdAndValue -> ligandIdAndValue.getId(),
                                                                         ligandIdAndValue -> ligandIdAndValue.getValue())));

                this.iupharModel.getLigandIdToDatabaseLinksMap()
                                .putAll(ligands.stream()
                                               .map(ligand -> ligand.getLigandId())
                                               .map(ligandId -> new IdAndFutureValue<>(ligandId,
                                                                                       executorService.submit(() -> this.restAccessor.getLigandDatabaseLinks(ligandId))))
                                               .filter(ligandIdAndValue -> ligandIdAndValue.getValue() != null)
                                               .collect(Collectors.toMap(ligandIdAndValue -> ligandIdAndValue.getId(),
                                                                         ligandIdAndValue -> ligandIdAndValue.getValue())));

                this.iupharModel.getTargetIdToFunctionsMap()
                                .putAll(targets.stream()
                                               .map(target -> target.getTargetId())
                                               .map(targetId -> new IdAndFutureValue<>(targetId,
                                                                                       executorService.submit(() -> this.restAccessor.getTargetFunction(targetId))))
                                               .filter(targetIdAndValue -> targetIdAndValue.getValue() != null)
                                               .collect(Collectors.toMap(targetIdAndValue -> targetIdAndValue.getId(),
                                                                         targetIdAndValue -> targetIdAndValue.getValue())));

                this.iupharModel.getTargetIdToDatabaseLinksMap()
                                .putAll(targets.stream()
                                               .map(target -> target.getTargetId())
                                               .map(targetId -> new IdAndFutureValue<>(targetId,
                                                                                       executorService.submit(() -> this.restAccessor.getTargetDatabaseLinks(targetId))))
                                               .filter(targetIdAndValue -> targetIdAndValue.getValue() != null)
                                               .collect(Collectors.toMap(targetIdAndValue -> targetIdAndValue.getId(),
                                                                         targetIdAndValue -> targetIdAndValue.getValue())));

                this.iupharModel.getTargetIdToSynonymsMap()
                                .putAll(targets.stream()
                                               .map(target -> target.getTargetId())
                                               .map(targetId -> new IdAndFutureValue<>(targetId,
                                                                                       executorService.submit(() -> this.restAccessor.getTargetSynonyms(targetId))))
                                               .filter(targetIdAndValue -> targetIdAndValue.getValue() != null)
                                               .collect(Collectors.toMap(targetIdAndValue -> targetIdAndValue.getId(),
                                                                         targetIdAndValue -> targetIdAndValue.getValue())));

                this.iupharModel.getTargetIdToGeneProteinInformationMap()
                                .putAll(targets.stream()
                                               .map(target -> target.getTargetId())
                                               .map(targetId -> new IdAndFutureValue<>(targetId,
                                                                                       executorService.submit(() -> this.restAccessor.getTargetGeneProteinInformation(targetId))))
                                               .filter(targetIdAndValue -> targetIdAndValue.getValue() != null)
                                               .collect(Collectors.toMap(targetIdAndValue -> targetIdAndValue.getId(),
                                                                         targetIdAndValue -> targetIdAndValue.getValue())));

                executorService.shutdown();
                executorService.awaitTermination(10, TimeUnit.MINUTES);
            }
            catch (Exception e)
            {
                LOG.error("", e);
            }

            return this;
        }

        @Override
        public IUpharModelManager loadFromFile(File file) throws IOException
        {
            this.iupharModel = JSONHelper.readFromString(FileUtils.readFileToString(file, "utf-8"), IUpharModel.class);
            return this;
        }

        @Override
        public IUpharModelManager saveToFile(File file) throws IOException
        {
            FileUtils.writeStringToFile(file, JSONHelper.prettyPrint(this.iupharModel), "utf-8");
            return this;
        }

        @Override
        public LigandManager findLigand(String name)
        {
            return this.createLigandManager(this.iupharModel.getLigands()
                                                            .stream()
                                                            .filter(ligand -> StringUtils.equalsIgnoreCase(ligand.getName(), name))
                                                            .findFirst()
                                                            .get());
        }

        private LigandManager createLigandManager(Ligand ligand)
        {
            return new LigandManager()
            {
                @Override
                public InteractionsWithTargetsManager findTargets()
                {
                    InteractionsWithTargets interactionsWithTargets = new InteractionsWithTargets(IUpharModelManagerImpl.this.iupharModel.getInteractions()
                                                                                                                                         .stream()
                                                                                                                                         .filter(interaction -> ligand != null)
                                                                                                                                         .filter(interaction -> ObjectUtils.equals(interaction.getLigandId(),
                                                                                                                                                                                   ligand.getLigandId()))
                                                                                                                                         .map(interaction -> new InteractionWithTarget(interaction,
                                                                                                                                                                                       IUpharModelManagerImpl.this.findTarget(interaction.getTargetId())))
                                                                                                                                         .collect(Collectors.toList()));

                    return this.createInteractionsWithTargetsManager(interactionsWithTargets);
                }

                private InteractionsWithTargetsManager createInteractionsWithTargetsManager(InteractionsWithTargets interactionsWithTargets)
                {
                    return new InteractionsWithTargetsManager()
                    {
                        @Override
                        public InteractionsWithTargets get()
                        {
                            return interactionsWithTargets;
                        }

                        @Override
                        public Stream<TargetInteractionAccessor> getInteractions()
                        {
                            return interactionsWithTargets.stream()
                                                          .map(interactionWithTarget -> new TargetInteractionAccessor()
                                                          {
                                                              @Override
                                                              public TargetAccessor getTarget()
                                                              {
                                                                  long targetId = interactionWithTarget.getTarget()
                                                                                                       .getTargetId();
                                                                  return IUpharModelManagerImpl.this.createTargetAccessor(targetId);
                                                              }

                                                              @Override
                                                              public InteractionPropertiesAccessor getProperties()
                                                              {
                                                                  return new InteractionPropertiesAccessorImpl(interactionWithTarget.getInteraction());
                                                              }

                                                          });
                        }

                    };
                }

                @Override
                public Ligand get()
                {
                    return ligand;
                }
            };
        }

        private LigandAccessor createLigandAccessor(long ligandId)
        {
            Ligand ligand = this.iupharModel.getLigandIdToLigand()
                                            .get(ligandId);
            return new LigandAccessor()
            {
                @Override
                public String getName()
                {
                    return ligand.getName();
                }

                @Override
                public String getNameHtmlStripped()
                {
                    return this.getName()
                               .replaceAll("\\<[^\\>]+\\>", "");
                }

                @Override
                public String getClinicalUse()
                {
                    return IUpharModelManagerImpl.this.iupharModel.getLigandIdToCommentsMap()
                                                                  .getOrDefault(ligandId, new Comments())
                                                                  .getClinicalUse();
                }

                @Override
                public LigandType getType()
                {
                    return ligand.getTypeAsEnum();
                }

                @Override
                public boolean hasType(LigandType ligandType)
                {
                    return ligandType.equals(this.getType());
                }

                @Override
                public Stream<TargetInteractionAccessor> getTargetInteractions()
                {
                    return IUpharModelManagerImpl.this.iupharModel.getLigandIdToInteraction()
                                                                  .get(ligandId)
                                                                  .stream()
                                                                  .map(interaction -> IUpharModelManagerImpl.this.createInteractionWithTarget(interaction));
                }
            };
        }

        private TargetAccessor createTargetAccessor(long targetId)
        {
            Target target = IUpharModelManagerImpl.this.iupharModel.getTargetIdToTarget()
                                                                   .get(targetId);
            return new TargetAccessor()
            {

                @Override
                public String getName()
                {
                    return target.getName();
                }

                @Override
                public String getHumanRelatedDatabaseId(Database database)
                {
                    return this.getDatabaseId(database, SpeciesType.HUMAN);
                }

                @Override
                public String getDatabaseId(Database database, SpeciesType speciesType)
                {
                    return IUpharModelManagerImpl.this.iupharModel.getTargetIdToDatabaseLinksMap()
                                                                  .getOrDefault(targetId, new DatabaseLinks())
                                                                  .stream()
                                                                  .filter(databaseLink -> databaseLink.hasDatabase(database)
                                                                          && databaseLink.hasSpeciesType(speciesType))
                                                                  .findFirst()
                                                                  .orElse(new DatabaseLink())
                                                                  .getAccession();
                }

                @Override
                public String getGene(SpeciesType speciesType)
                {
                    return IUpharModelManagerImpl.this.iupharModel.getTargetIdToGeneProteinInformationMap()
                                                                  .getOrDefault(targetId, new GeneProteinInformations())
                                                                  .stream()
                                                                  .filter(geneProteinInfo -> geneProteinInfo.hasSpeciesType(speciesType))
                                                                  .findFirst()
                                                                  .orElse(new GeneProteinInformation())
                                                                  .getGeneSymbol();
                }

                @Override
                public String getHumanGene()
                {
                    return this.getGene(SpeciesType.HUMAN);
                }

                @Override
                public Stream<LigandInteractionAccessor> getLigandInteractions()
                {
                    return IUpharModelManagerImpl.this.iupharModel.getTargetIdToInteraction()
                                                                  .getOrDefault(targetId, Collections.emptyList())
                                                                  .stream()
                                                                  .map(interaction -> IUpharModelManagerImpl.this.createInteractionWithLigand(interaction));
                }

            };
        }

        private Target findTarget(long targetId)
        {
            return this.iupharModel.getTargets()
                                   .stream()
                                   .filter(target -> ObjectUtils.equals(targetId, target.getTargetId()))
                                   .findFirst()
                                   .get();
        }

        @Override
        public LigandManager findLigand(long ligandId)
        {
            return this.createLigandManager(this.iupharModel.getLigands()
                                                            .stream()
                                                            .filter(ligand -> ObjectUtils.equals(ligand.getLigandId(), ligandId))
                                                            .findFirst()
                                                            .get());
        }

        @Override
        public LigandManager findLigandForMetaboliteOld(String metabolite)
        {
            return this.createLigandManager(this.iupharModel.getLigands()
                                                            .stream()
                                                            .filter(ligand -> ligand.hasType(LigandType.METABOLITE))
                                                            .filter(ligand -> StringUtils.equalsIgnoreCase(ligand.getName(), metabolite)
                                                                    || this.iupharModel.getLigandIdToSynonymsMap()
                                                                                       .getOrDefault(ligand.getLigandId(), new Synonyms())
                                                                                       .stream()
                                                                                       .anyMatch(synonym -> StringUtils.equalsIgnoreCase(synonym.getName(),
                                                                                                                                         metabolite)))
                                                            .findFirst()
                                                            .orElseGet(() -> null));
        }

        @Override
        public Stream<LigandAccessor> findLigandForMetabolite(String metabolite)
        {
            return this.iupharModel.getLigands()
                                   .stream()
                                   .filter(ligand -> ligand.hasType(LigandType.METABOLITE))
                                   .filter(ligand -> StringUtils.equalsIgnoreCase(ligand.getName(), metabolite) || this.iupharModel.getLigandIdToSynonymsMap()
                                                                                                                                   .getOrDefault(ligand.getLigandId(),
                                                                                                                                                 new Synonyms())
                                                                                                                                   .stream()
                                                                                                                                   .anyMatch(synonym -> StringUtils.equalsIgnoreCase(synonym.getName(),
                                                                                                                                                                                     metabolite)))
                                   .map(ligand -> new LigandAccessor()
                                   {
                                       @Override
                                       public Stream<TargetInteractionAccessor> getTargetInteractions()
                                       {
                                           return IUpharModelManagerImpl.this.iupharModel.getLigandIdToInteraction()
                                                                                         .get(ligand.getLigandId())
                                                                                         .stream()
                                                                                         .map(interaction -> IUpharModelManagerImpl.this.createInteractionWithTarget(interaction));
                                       }

                                       @Override
                                       public String getName()
                                       {
                                           return ligand.getName();
                                       }

                                       @Override
                                       public String getNameHtmlStripped()
                                       {
                                           return this.getName()
                                                      .replaceAll("\\<[^\\>]+\\>", "");
                                       }

                                       @Override
                                       public String getClinicalUse()
                                       {
                                           return IUpharModelManagerImpl.this.iupharModel.getLigandIdToCommentsMap()
                                                                                         .get(ligand.getLigandId())
                                                                                         .getClinicalUse();
                                       }

                                       @Override
                                       public LigandType getType()
                                       {
                                           return ligand.getTypeAsEnum();
                                       }

                                       @Override
                                       public boolean hasType(LigandType ligandType)
                                       {
                                           return ligandType.equals(this.getType());
                                       }
                                   });
        }

        private TargetInteractionAccessor createInteractionWithTarget(InteractionShort interaction)
        {
            Long targetId = interaction.getTargetId();

            return new TargetInteractionAccessor()
            {
                @Override
                public TargetAccessor getTarget()
                {
                    return IUpharModelManagerImpl.this.createTargetAccessor(targetId);
                }

                @Override
                public InteractionPropertiesAccessor getProperties()
                {
                    return new InteractionPropertiesAccessorImpl(interaction);
                }

            };
        }

        private LigandInteractionAccessor createInteractionWithLigand(InteractionShort interaction)
        {
            Long ligandId = interaction.getLigandId();
            return new LigandInteractionAccessor()
            {
                @Override
                public InteractionPropertiesAccessor getProperties()
                {
                    return new InteractionPropertiesAccessorImpl(interaction);
                }

                @Override
                public LigandAccessor getLigand()
                {
                    return IUpharModelManagerImpl.this.createLigandAccessor(ligandId);
                }
            };
        }

        @Override
        public TargetManager findTargetByName(String name)
        {
            return this.createTargetManager(this.iupharModel.getTargets()
                                                            .stream()
                                                            .filter(target -> StringUtils.equalsIgnoreCase(target.getName(), name)
                                                                    || this.iupharModel.getTargetIdToSynonymsMap()
                                                                                       .getOrDefault(target.getTargetId(), new Synonyms())
                                                                                       .stream()
                                                                                       .anyMatch(synonym -> StringUtils.equalsIgnoreCase(synonym.getName(),
                                                                                                                                         name)))
                                                            .findFirst()
                                                            .get());
        }

        private TargetManager createTargetManager(Target target)
        {
            return new TargetManager()
            {
                @Override
                public InteractionsWithLigandsManager findLigands()
                {
                    return this.createInteractionsWithLigandsManager(new InteractionsWithLigands(IUpharModelManagerImpl.this.iupharModel.getInteractions()
                                                                                                                                        .stream()
                                                                                                                                        .filter(interaction -> ObjectUtils.equals(interaction.getTargetId(),
                                                                                                                                                                                  target.getTargetId()))
                                                                                                                                        .map(interaction -> new InteractionWithLigand(interaction,
                                                                                                                                                                                      IUpharModelManagerImpl.this.findLigand(interaction.getLigandId())
                                                                                                                                                                                                                 .get(),
                                                                                                                                                                                      IUpharModelManagerImpl.this.iupharModel.getLigandIdToCommentsMap()
                                                                                                                                                                                                                             .get(interaction.getLigandId())))
                                                                                                                                        .collect(Collectors.toList())));
                }

                private InteractionsWithLigandsManager createInteractionsWithLigandsManager(InteractionsWithLigands interactionsWithLigands)
                {
                    return new InteractionsWithLigandsManager()
                    {
                        @Override
                        public InteractionsWithLigands get()
                        {
                            return interactionsWithLigands;
                        }
                    };
                }
            };
        }

    }

    public static IUpharModelManagerLoader getInstance()
    {
        return new IUpharModelManagerImpl();
    }
}
