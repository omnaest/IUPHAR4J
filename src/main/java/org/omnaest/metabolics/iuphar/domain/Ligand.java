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

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Ligand
{
	private Long		ligandId;
	private String		name;
	private String		abbreviation;
	private String		inn;
	private String		type;
	private String		species;
	private Boolean		radioactive;
	private Boolean		labelled;
	private Boolean		approved;
	private Boolean		withdrawn;
	private String		approvalSource;
	private List<Long>	subunitIds;
	private List<Long>	complexIds;
	private List<Long>	prodrugIds;
	private List<Long>	activeDrugIds;

	public static enum Type
	{
		Metabolite("Metabolite"), Synthetic_organic("Synthetic organic");

		private String type;

		private Type(String type)
		{
			this.type = type;
		}

		public static Type fromType(String type)
		{
			return Arrays	.asList(values())
							.stream()
							.filter(value -> value.type.equalsIgnoreCase(type))
							.findFirst()
							.orElseGet(() -> null);
		}
	}

	public boolean hasType(Type type)
	{
		return type != null && type.equals(this.getTypeAsEnum());
	}

	@JsonIgnore
	public Type getTypeAsEnum()
	{
		return Type.fromType(this.type);
	}

	public Long getLigandId()
	{
		return this.ligandId;
	}

	public void setLigandId(Long ligandId)
	{
		this.ligandId = ligandId;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAbbreviation()
	{
		return this.abbreviation;
	}

	public void setAbbreviation(String abbreviation)
	{
		this.abbreviation = abbreviation;
	}

	public String getInn()
	{
		return this.inn;
	}

	public void setInn(String inn)
	{
		this.inn = inn;
	}

	public String getType()
	{
		return this.type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getSpecies()
	{
		return this.species;
	}

	public void setSpecies(String species)
	{
		this.species = species;
	}

	public Boolean getRadioactive()
	{
		return this.radioactive;
	}

	public void setRadioactive(Boolean radioactive)
	{
		this.radioactive = radioactive;
	}

	public Boolean getLabelled()
	{
		return this.labelled;
	}

	public void setLabelled(Boolean labelled)
	{
		this.labelled = labelled;
	}

	public Boolean getApproved()
	{
		return this.approved;
	}

	public void setApproved(Boolean approved)
	{
		this.approved = approved;
	}

	public Boolean getWithdrawn()
	{
		return this.withdrawn;
	}

	public void setWithdrawn(Boolean withdrawn)
	{
		this.withdrawn = withdrawn;
	}

	public String getApprovalSource()
	{
		return this.approvalSource;
	}

	public void setApprovalSource(String approvalSource)
	{
		this.approvalSource = approvalSource;
	}

	public List<Long> getSubunitIds()
	{
		return this.subunitIds;
	}

	public void setSubunitIds(List<Long> subunitIds)
	{
		this.subunitIds = subunitIds;
	}

	public List<Long> getComplexIds()
	{
		return this.complexIds;
	}

	public void setComplexIds(List<Long> complexIds)
	{
		this.complexIds = complexIds;
	}

	public List<Long> getProdrugIds()
	{
		return this.prodrugIds;
	}

	public void setProdrugIds(List<Long> prodrugIds)
	{
		this.prodrugIds = prodrugIds;
	}

	public List<Long> getActiveDrugIds()
	{
		return this.activeDrugIds;
	}

	public void setActiveDrugIds(List<Long> activeDrugIds)
	{
		this.activeDrugIds = activeDrugIds;
	}

}
