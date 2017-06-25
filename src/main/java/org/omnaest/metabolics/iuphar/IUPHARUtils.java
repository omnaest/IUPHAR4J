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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.omnaest.metabolics.iuphar.domain.InteractionsShort;
import org.omnaest.metabolics.iuphar.domain.Ligand;
import org.omnaest.metabolics.iuphar.domain.Ligands;
import org.omnaest.metabolics.iuphar.domain.Synonyms;
import org.omnaest.metabolics.iuphar.domain.Target;
import org.omnaest.metabolics.iuphar.domain.Targets;
import org.omnaest.metabolics.iuphar.utils.IdAndFutureValue;
import org.omnaest.metabolics.iuphar.utils.JSONHelper;
import org.omnaest.metabolics.iuphar.wrapper.IUPHARInteractionsWithLigandsManager;
import org.omnaest.metabolics.iuphar.wrapper.IUPHARInteractionsWithTargetsManager;
import org.omnaest.metabolics.iuphar.wrapper.IUPHARLigandManager;
import org.omnaest.metabolics.iuphar.wrapper.IUPHARModelManager;
import org.omnaest.metabolics.iuphar.wrapper.IUPHARModelManagerLoader;
import org.omnaest.metabolics.iuphar.wrapper.IUPHARTargetManager;
import org.omnaest.metabolics.iuphar.wrapper.domain.IUPHARModel;
import org.omnaest.metabolics.iuphar.wrapper.domain.InteractionWithLigand;
import org.omnaest.metabolics.iuphar.wrapper.domain.InteractionWithTarget;
import org.omnaest.metabolics.iuphar.wrapper.domain.InteractionsWithLigands;
import org.omnaest.metabolics.iuphar.wrapper.domain.InteractionsWithTargets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IUPHARUtils
{
	private final static Logger LOG = LoggerFactory.getLogger(IUPHARUtils.class);

	public static IUPHARModelManagerLoader getInstance()
	{
		return new IUPHARModelManager()
		{
			private IUPHARModel iupharModel;

			@Override
			public IUPHARModelManager loadFromRestApi()
			{
				Ligands ligands = IUPHARRestApiUtils.getLigands();
				Targets targets = IUPHARRestApiUtils.getTargets();
				InteractionsShort interactions = IUPHARRestApiUtils.getInteractions();

				this.iupharModel = new IUPHARModel(ligands, targets, interactions);

				try
				{
					ExecutorService executorService = Executors.newFixedThreadPool(4);

					this.iupharModel.getLigandIdToSynonymsMap()
									.putAll(ligands	.stream()
													.map(ligand -> ligand.getLigandId())
													.map(ligandId -> new IdAndFutureValue<>(ligandId,
																							executorService.submit(() -> IUPHARRestApiUtils.getLigandSynonyms(ligandId))))
													.filter(ligandIdAndValue -> ligandIdAndValue.getValue() != null)
													.collect(Collectors.toMap(	ligandIdAndValue -> ligandIdAndValue.getId(),
																				ligandIdAndValue -> ligandIdAndValue.getValue())));

					this.iupharModel.getLigandIdToDatabaseLinksMap()
									.putAll(ligands	.stream()
													.map(ligand -> ligand.getLigandId())
													.map(ligandId -> new IdAndFutureValue<>(ligandId,
																							executorService.submit(() -> IUPHARRestApiUtils.getLigandDatabaseLinks(ligandId))))
													.filter(ligandIdAndValue -> ligandIdAndValue.getValue() != null)
													.collect(Collectors.toMap(	ligandIdAndValue -> ligandIdAndValue.getId(),
																				ligandIdAndValue -> ligandIdAndValue.getValue())));

					this.iupharModel.getTargetIdToFunctionsMap()
									.putAll(targets	.stream()
													.map(target -> target.getTargetId())
													.map(targetId -> new IdAndFutureValue<>(targetId,
																							executorService.submit(() -> IUPHARRestApiUtils.getTargetFunction(targetId))))
													.filter(targetIdAndValue -> targetIdAndValue.getValue() != null)
													.collect(Collectors.toMap(	targetIdAndValue -> targetIdAndValue.getId(),
																				targetIdAndValue -> targetIdAndValue.getValue())));

					this.iupharModel.getTargetIdToDatabaseLinksMap()
									.putAll(targets	.stream()
													.map(target -> target.getTargetId())
													.map(targetId -> new IdAndFutureValue<>(targetId,
																							executorService.submit(() -> IUPHARRestApiUtils.getTargetDatabaseLinks(targetId))))
													.filter(targetIdAndValue -> targetIdAndValue.getValue() != null)
													.collect(Collectors.toMap(	targetIdAndValue -> targetIdAndValue.getId(),
																				targetIdAndValue -> targetIdAndValue.getValue())));

					this.iupharModel.getTargetIdToSynonymsMap()
									.putAll(targets	.stream()
													.map(target -> target.getTargetId())
													.map(targetId -> new IdAndFutureValue<>(targetId,
																							executorService.submit(() -> IUPHARRestApiUtils.getTargetSynonyms(targetId))))
													.filter(targetIdAndValue -> targetIdAndValue.getValue() != null)
													.collect(Collectors.toMap(	targetIdAndValue -> targetIdAndValue.getId(),
																				targetIdAndValue -> targetIdAndValue.getValue())));

					executorService.shutdown();
					executorService.awaitTermination(10, TimeUnit.MINUTES);
				} catch (Exception e)
				{
					LOG.error("", e);
				}

				return this;
			}

			@Override
			public IUPHARModelManager loadFromFile(File file) throws IOException
			{
				this.iupharModel = JSONHelper.readFromString(FileUtils.readFileToString(file, "utf-8"), IUPHARModel.class);
				return this;
			}

			@Override
			public IUPHARModelManager saveToFile(File file) throws IOException
			{
				FileUtils.writeStringToFile(file, JSONHelper.prettyPrint(this.iupharModel), "utf-8");
				return this;
			}

			@Override
			public IUPHARLigandManager findLigand(String name)
			{
				return this.createLigandManager(this.iupharModel.getLigands()
																.stream()
																.filter(ligand -> StringUtils.equalsIgnoreCase(ligand.getName(), name))
																.findFirst()
																.get());
			}

			private IUPHARLigandManager createLigandManager(Ligand ligand)
			{
				return new IUPHARLigandManager()
				{
					@Override
					public IUPHARInteractionsWithTargetsManager findTargets()
					{
						InteractionsWithTargets interactionsWithTargets = new InteractionsWithTargets(iupharModel	.getInteractions()
																													.stream()
																													.filter(interaction -> ObjectUtils.equals(	interaction.getLigandId(),
																																								ligand.getLigandId()))
																													.map(interaction -> new InteractionWithTarget(	interaction,
																																									findTarget(interaction.getTargetId())))
																													.collect(Collectors.toList()));

						return this.createInteractionsWithTargetsManager(interactionsWithTargets);
					}

					private IUPHARInteractionsWithTargetsManager createInteractionsWithTargetsManager(InteractionsWithTargets interactionsWithTargets)
					{
						return new IUPHARInteractionsWithTargetsManager()
						{
							@Override
							public InteractionsWithTargets get()
							{
								return interactionsWithTargets;
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

			private Target findTarget(long targetId)
			{
				return this.iupharModel	.getTargets()
										.stream()
										.filter(target -> ObjectUtils.equals(targetId, target.getTargetId()))
										.findFirst()
										.get();
			}

			@Override
			public IUPHARLigandManager findLigand(long ligandId)
			{
				return this.createLigandManager(this.iupharModel.getLigands()
																.stream()
																.filter(ligand -> ObjectUtils.equals(ligand.getLigandId(), ligandId))
																.findFirst()
																.get());
			}

			@Override
			public IUPHARLigandManager findLigandForMetabolite(String metabolite)
			{
				return this.createLigandManager(this.iupharModel.getLigands()
																.stream()
																.filter(ligand -> ligand.hasType(Ligand.Type.Metabolite))
																.filter(ligand -> StringUtils.equalsIgnoreCase(ligand.getName(), metabolite)
																		|| this.iupharModel	.getLigandIdToSynonymsMap()
																							.getOrDefault(ligand.getLigandId(), new Synonyms())
																							.stream()
																							.anyMatch(synonym -> StringUtils.equalsIgnoreCase(	synonym.getName(),
																																				metabolite)))
																.findFirst()
																.get());
			}

			@Override
			public IUPHARTargetManager findTargetByName(String name)
			{
				return this.createTargetManager(this.iupharModel.getTargets()
																.stream()
																.filter(target -> StringUtils.equalsIgnoreCase(target.getName(), name)
																		|| this.iupharModel	.getTargetIdToSynonymsMap()
																							.getOrDefault(target.getTargetId(), new Synonyms())
																							.stream()
																							.anyMatch(synonym -> StringUtils.equalsIgnoreCase(	synonym.getName(),
																																				name)))
																.findFirst()
																.get());
			}

			private IUPHARTargetManager createTargetManager(Target target)
			{
				return new IUPHARTargetManager()
				{
					@Override
					public IUPHARInteractionsWithLigandsManager findLigands()
					{
						return this.createInteractionsWithLigandsManager(new InteractionsWithLigands(iupharModel.getInteractions()
																												.stream()
																												.filter(interaction -> ObjectUtils.equals(	interaction.getTargetId(),
																																							target.getTargetId()))
																												.map(interaction -> new InteractionWithLigand(	interaction,
																																								findLigand(interaction.getLigandId()).get()))
																												.collect(Collectors.toList())));
					}

					private IUPHARInteractionsWithLigandsManager createInteractionsWithLigandsManager(InteractionsWithLigands interactionsWithLigands)
					{
						return new IUPHARInteractionsWithLigandsManager()
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
		};
	}
}
