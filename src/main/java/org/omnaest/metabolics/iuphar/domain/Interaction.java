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
package org.omnaest.metabolics.iuphar.domain;

public class Interaction
{
	private Long		interactionId;
	private Long		targetId;
	private Long		ligandAsTargetId;
	private String		targetSpecies;
	private Boolean		primaryTarget;
	private String		targetBindingSite;
	private Long		ligandId;
	private String		ligandContext;
	private Boolean		endogenous;
	private String		type;
	private String		action;
	private String		actionComment;
	private String		selectivity;
	private String		concentrationRange;
	private String		affinity;
	private String		affinityType;
	private String		originalAffinity;
	private String		originalAffinityType;
	private String		originalAffinityRelation;
	private String		assayDescription;
	private String		assayConditions;
	private Boolean		useDependent;
	private Boolean		voltageDependent;
	private String		voltage;
	private Boolean		physiologicalVoltage;
	private Boolean		conciseView;
	private References	refs;

	public Long getInteractionId()
	{
		return this.interactionId;
	}

	public void setInteractionId(Long interactionId)
	{
		this.interactionId = interactionId;
	}

	public Long getTargetId()
	{
		return this.targetId;
	}

	public void setTargetId(Long targetId)
	{
		this.targetId = targetId;
	}

	public Long getLigandAsTargetId()
	{
		return this.ligandAsTargetId;
	}

	public void setLigandAsTargetId(Long ligandAsTargetId)
	{
		this.ligandAsTargetId = ligandAsTargetId;
	}

	public String getTargetSpecies()
	{
		return this.targetSpecies;
	}

	public void setTargetSpecies(String targetSpecies)
	{
		this.targetSpecies = targetSpecies;
	}

	public Boolean getPrimaryTarget()
	{
		return this.primaryTarget;
	}

	public void setPrimaryTarget(Boolean primaryTarget)
	{
		this.primaryTarget = primaryTarget;
	}

	public String getTargetBindingSite()
	{
		return this.targetBindingSite;
	}

	public void setTargetBindingSite(String targetBindingSite)
	{
		this.targetBindingSite = targetBindingSite;
	}

	public Long getLigandId()
	{
		return this.ligandId;
	}

	public void setLigandId(Long ligandId)
	{
		this.ligandId = ligandId;
	}

	public String getLigandContext()
	{
		return this.ligandContext;
	}

	public void setLigandContext(String ligandContext)
	{
		this.ligandContext = ligandContext;
	}

	public Boolean getEndogenous()
	{
		return this.endogenous;
	}

	public void setEndogenous(Boolean endogenous)
	{
		this.endogenous = endogenous;
	}

	public String getType()
	{
		return this.type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getAction()
	{
		return this.action;
	}

	public void setAction(String action)
	{
		this.action = action;
	}

	public String getActionComment()
	{
		return this.actionComment;
	}

	public void setActionComment(String actionComment)
	{
		this.actionComment = actionComment;
	}

	public String getSelectivity()
	{
		return this.selectivity;
	}

	public void setSelectivity(String selectivity)
	{
		this.selectivity = selectivity;
	}

	public String getConcentrationRange()
	{
		return this.concentrationRange;
	}

	public void setConcentrationRange(String concentrationRange)
	{
		this.concentrationRange = concentrationRange;
	}

	public String getAffinity()
	{
		return this.affinity;
	}

	public void setAffinity(String affinity)
	{
		this.affinity = affinity;
	}

	public String getAffinityType()
	{
		return this.affinityType;
	}

	public void setAffinityType(String affinityType)
	{
		this.affinityType = affinityType;
	}

	public String getOriginalAffinity()
	{
		return this.originalAffinity;
	}

	public void setOriginalAffinity(String originalAffinity)
	{
		this.originalAffinity = originalAffinity;
	}

	public String getOriginalAffinityType()
	{
		return this.originalAffinityType;
	}

	public void setOriginalAffinityType(String originalAffinityType)
	{
		this.originalAffinityType = originalAffinityType;
	}

	public String getOriginalAffinityRelation()
	{
		return this.originalAffinityRelation;
	}

	public void setOriginalAffinityRelation(String originalAffinityRelation)
	{
		this.originalAffinityRelation = originalAffinityRelation;
	}

	public String getAssayDescription()
	{
		return this.assayDescription;
	}

	public void setAssayDescription(String assayDescription)
	{
		this.assayDescription = assayDescription;
	}

	public String getAssayConditions()
	{
		return this.assayConditions;
	}

	public void setAssayConditions(String assayConditions)
	{
		this.assayConditions = assayConditions;
	}

	public Boolean getUseDependent()
	{
		return this.useDependent;
	}

	public void setUseDependent(Boolean useDependent)
	{
		this.useDependent = useDependent;
	}

	public Boolean getVoltageDependent()
	{
		return this.voltageDependent;
	}

	public void setVoltageDependent(Boolean voltageDependent)
	{
		this.voltageDependent = voltageDependent;
	}

	public String getVoltage()
	{
		return this.voltage;
	}

	public void setVoltage(String voltage)
	{
		this.voltage = voltage;
	}

	public Boolean getPhysiologicalVoltage()
	{
		return this.physiologicalVoltage;
	}

	public void setPhysiologicalVoltage(Boolean physiologicalVoltage)
	{
		this.physiologicalVoltage = physiologicalVoltage;
	}

	public Boolean getConciseView()
	{
		return this.conciseView;
	}

	public void setConciseView(Boolean conciseView)
	{
		this.conciseView = conciseView;
	}

	public References getRefs()
	{
		return this.refs;
	}

	public void setRefs(References refs)
	{
		this.refs = refs;
	}

}
