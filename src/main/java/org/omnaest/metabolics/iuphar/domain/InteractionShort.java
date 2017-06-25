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

import java.util.List;

public class InteractionShort
{
	private Long		targetId;
	private Long		ligandAsTargetId;
	private String		targetSpecies;
	private Boolean		primaryTarget;
	private Long		ligandId;
	private String		ligandContext;
	private String		type;
	private String		action;
	private String		affinity;
	private String		affinityType;
	private List<Long>	refIds;
	private List<Long>	dataPointInteractionIds;

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

	public List<Long> getRefIds()
	{
		return this.refIds;
	}

	public void setRefIds(List<Long> refIds)
	{
		this.refIds = refIds;
	}

	public List<Long> getDataPointInteractionIds()
	{
		return this.dataPointInteractionIds;
	}

	public void setDataPointInteractionIds(List<Long> dataPointInteractionIds)
	{
		this.dataPointInteractionIds = dataPointInteractionIds;
	}

}
