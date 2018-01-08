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
package org.omnaest.metabolics.iuphar.wrapper;

import java.io.File;
import java.io.IOException;

import org.omnaest.utils.cache.Cache;
import org.omnaest.utils.rest.client.RestClient.Proxy;

public interface IUPHARModelManagerLoader
{
    IUPHARModelManagerLoader usingLocalCache();

    IUPHARModelManagerLoader usingCache(Cache cache);

    IUPHARModelManagerLoader usingProxy(Proxy proxy);

    IUPHARModelManager loadFromFile(File file) throws IOException;

    IUPHARModelManager loadFromRestApi();
}
