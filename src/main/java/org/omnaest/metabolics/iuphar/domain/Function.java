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

public class Function
{
	private Long		targetId;
	private String		property;
	private String		description;
	private String		tissue;
	private String		technique;
	private String		pathophysiology;
	private String		species;
	private References	refs;

	public Long getTargetId()
	{
		return this.targetId;
	}

	public void setTargetId(Long targetId)
	{
		this.targetId = targetId;
	}

	public String getProperty()
	{
		return this.property;
	}

	public void setProperty(String property)
	{
		this.property = property;
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getTissue()
	{
		return this.tissue;
	}

	public void setTissue(String tissue)
	{
		this.tissue = tissue;
	}

	public String getTechnique()
	{
		return this.technique;
	}

	public void setTechnique(String technique)
	{
		this.technique = technique;
	}

	public String getPathophysiology()
	{
		return this.pathophysiology;
	}

	public void setPathophysiology(String pathophysiology)
	{
		this.pathophysiology = pathophysiology;
	}

	public String getSpecies()
	{
		return this.species;
	}

	public void setSpecies(String species)
	{
		this.species = species;
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
