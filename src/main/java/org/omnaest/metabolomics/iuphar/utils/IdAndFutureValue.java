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
package org.omnaest.metabolomics.iuphar.utils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdAndFutureValue<E>
{
	private final static Logger LOG = LoggerFactory.getLogger(IdAndFutureValue.class);

	private Long		id;
	private Future<E>	value;

	public IdAndFutureValue(Long id, Future<E> value)
	{
		super();
		this.id = id;
		this.value = value;
	}

	public Long getId()
	{
		return this.id;
	}

	public E getValue()
	{
		try
		{
			return this.value.get();
		} catch (InterruptedException | ExecutionException e)
		{
			LOG.error("", e);
			return null;
		}
	}

}
