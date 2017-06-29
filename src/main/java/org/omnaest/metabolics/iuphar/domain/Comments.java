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

public class Comments
{
	private String	comments;
	private String	bioactivityComments;
	private String	clinicalUse;
	private String	mechanismOfAction;
	private String	absorptionAndDistribution;
	private String	metabolism;
	private String	elimination;
	private String	populationPharmacokinetics;
	private String	organFunctionImpairment;
	private String	mutationsAndPathophysiology;

	public String getComments()
	{
		return this.comments;
	}

	public void setComments(String comments)
	{
		this.comments = comments;
	}

	public String getBioactivityComments()
	{
		return this.bioactivityComments;
	}

	public void setBioactivityComments(String bioactivityComments)
	{
		this.bioactivityComments = bioactivityComments;
	}

	public String getClinicalUse()
	{
		return this.clinicalUse;
	}

	public void setClinicalUse(String clinicalUse)
	{
		this.clinicalUse = clinicalUse;
	}

	public String getMechanismOfAction()
	{
		return this.mechanismOfAction;
	}

	public void setMechanismOfAction(String mechanismOfAction)
	{
		this.mechanismOfAction = mechanismOfAction;
	}

	public String getAbsorptionAndDistribution()
	{
		return this.absorptionAndDistribution;
	}

	public void setAbsorptionAndDistribution(String absorptionAndDistribution)
	{
		this.absorptionAndDistribution = absorptionAndDistribution;
	}

	public String getMetabolism()
	{
		return this.metabolism;
	}

	public void setMetabolism(String metabolism)
	{
		this.metabolism = metabolism;
	}

	public String getElimination()
	{
		return this.elimination;
	}

	public void setElimination(String elimination)
	{
		this.elimination = elimination;
	}

	public String getPopulationPharmacokinetics()
	{
		return this.populationPharmacokinetics;
	}

	public void setPopulationPharmacokinetics(String populationPharmacokinetics)
	{
		this.populationPharmacokinetics = populationPharmacokinetics;
	}

	public String getOrganFunctionImpairment()
	{
		return this.organFunctionImpairment;
	}

	public void setOrganFunctionImpairment(String organFunctionImpairment)
	{
		this.organFunctionImpairment = organFunctionImpairment;
	}

	public String getMutationsAndPathophysiology()
	{
		return this.mutationsAndPathophysiology;
	}

	public void setMutationsAndPathophysiology(String mutationsAndPathophysiology)
	{
		this.mutationsAndPathophysiology = mutationsAndPathophysiology;
	}

}
