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

public class Target
{
	private Long		targetId;
	private String		name;
	private String		abbreviation;
	private String		systematicName;
	private String		type;
	private List<Long>	familyIds;
	private List<Long>	subunitIds;
	private List<Long>	complexIds;

	public Long getTargetId()
	{
		return this.targetId;
	}

	public void setTargetId(Long targetId)
	{
		this.targetId = targetId;
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

	public String getSystematicName()
	{
		return this.systematicName;
	}

	public void setSystematicName(String systematicName)
	{
		this.systematicName = systematicName;
	}

	public String getType()
	{
		return this.type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public List<Long> getFamilyIds()
	{
		return this.familyIds;
	}

	public void setFamilyIds(List<Long> familyIds)
	{
		this.familyIds = familyIds;
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

}
