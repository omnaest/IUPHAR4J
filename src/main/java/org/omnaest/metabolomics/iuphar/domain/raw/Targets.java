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
package org.omnaest.metabolomics.iuphar.domain.raw;

import java.util.ArrayList;
import java.util.List;

import org.omnaest.utils.JSONHelper;

public class Targets extends ArrayList<Target>
{
    private static final long serialVersionUID = 4029533051260191930L;

    public Targets(List<Target> targets)
    {
        this.addAll(targets);
    }

    public Targets()
    {
        super();
    }

    @Override
    public String toString()
    {
        return JSONHelper.prettyPrint(this);
    }
}
