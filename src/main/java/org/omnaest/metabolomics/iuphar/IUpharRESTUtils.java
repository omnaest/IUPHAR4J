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
package org.omnaest.metabolomics.iuphar;

import org.omnaest.metabolomics.iuphar.domain.raw.Comments;
import org.omnaest.metabolomics.iuphar.domain.raw.DatabaseLinks;
import org.omnaest.metabolomics.iuphar.domain.raw.Functions;
import org.omnaest.metabolomics.iuphar.domain.raw.GeneProteinInformations;
import org.omnaest.metabolomics.iuphar.domain.raw.Interactions;
import org.omnaest.metabolomics.iuphar.domain.raw.InteractionsShort;
import org.omnaest.metabolomics.iuphar.domain.raw.Ligand;
import org.omnaest.metabolomics.iuphar.domain.raw.Ligands;
import org.omnaest.metabolomics.iuphar.domain.raw.Synonyms;
import org.omnaest.metabolomics.iuphar.domain.raw.Targets;
import org.omnaest.utils.cache.Cache;
import org.omnaest.utils.rest.client.RestClient;
import org.omnaest.utils.rest.client.RestClient.Proxy;
import org.omnaest.utils.rest.client.internal.JSONRestClient;

public class IUpharRESTUtils
{

    public static interface IUpharRestAccessor
    {
        IUpharRestAccessor withBaseUrl(String baseUrl);

        IUpharRestAccessor withProxy(Proxy proxy);

        IUpharRestAccessor usingCache(Cache cache);

        Comments getLigandComments(Long ligandId);

        Ligands getLigands();

        DatabaseLinks getLigandDatabaseLinks(long ligandId);

        Synonyms getLigandSynonyms(long ligandId);

        Ligand getLigand(Long ligandId);

        InteractionsShort getInteractions();

        Interactions getTargetInteractions(Long targetId);

        DatabaseLinks getTargetDatabaseLinks(long targetId);

        Synonyms getTargetSynonyms(long targetId);

        GeneProteinInformations getTargetGeneProteinInformation(long targetId);

        Functions getTargetFunction(long targetId);

        Targets getTargets();
    }

    public static IUpharRestAccessor getInstance()
    {
        return new IUpharRestAccessor()
        {
            private String baseUrl = "http://www.guidetopharmacology.org/services";
            private Cache  cache;
            private Proxy  proxy;

            @Override
            public IUpharRestAccessor withBaseUrl(String baseUrl)
            {
                this.baseUrl = baseUrl;
                return this;
            }

            @Override
            public IUpharRestAccessor usingCache(Cache cache)
            {
                this.cache = cache;
                return this;
            }

            @Override
            public IUpharRestAccessor withProxy(Proxy proxy)
            {
                this.proxy = proxy;
                return this;
            }

            private RestClient getRestClient()
            {
                return new JSONRestClient().withProxy(this.proxy)
                                           .withCache(this.cache);
            }

            @Override
            public Targets getTargets()
            {
                return this.getRestClient()
                           .requestGet(this.baseUrl + "/targets", Targets.class);
            }

            @Override
            public Functions getTargetFunction(long targetId)
            {
                return this.getRestClient()
                           .requestGet(this.baseUrl + "/targets/" + targetId + "/function", Functions.class);
            }

            @Override
            public GeneProteinInformations getTargetGeneProteinInformation(long targetId)
            {
                return this.getRestClient()
                           .requestGet(this.baseUrl + "/targets/" + targetId + "/geneProteinInformation", GeneProteinInformations.class);
            }

            @Override
            public Synonyms getTargetSynonyms(long targetId)
            {
                return this.getRestClient()
                           .requestGet(this.baseUrl + "/targets/" + targetId + "/synonyms", Synonyms.class);
            }

            @Override
            public DatabaseLinks getTargetDatabaseLinks(long targetId)
            {
                return this.getRestClient()
                           .requestGet(this.baseUrl + "/targets/" + targetId + "/databaseLinks", DatabaseLinks.class);
            }

            @Override
            public Interactions getTargetInteractions(Long targetId)
            {
                return this.getRestClient()
                           .requestGet(this.baseUrl + "/targets/" + targetId + "/interactions", Interactions.class);
            }

            @Override
            public InteractionsShort getInteractions()
            {
                return this.getRestClient()
                           .requestGet(this.baseUrl + "/interactions", InteractionsShort.class);
            }

            @Override
            public Ligand getLigand(Long ligandId)
            {
                return this.getRestClient()
                           .requestGet(this.baseUrl + "/ligands/" + ligandId, Ligand.class);
            }

            @Override
            public Synonyms getLigandSynonyms(long ligandId)
            {
                return this.getRestClient()
                           .requestGet(this.baseUrl + "/ligands/" + ligandId + "/synonyms", Synonyms.class);
            }

            @Override
            public DatabaseLinks getLigandDatabaseLinks(long ligandId)
            {
                return this.getRestClient()
                           .requestGet(this.baseUrl + "/ligands/" + ligandId + "/databaseLinks", DatabaseLinks.class);
            }

            @Override
            public Ligands getLigands()
            {
                return this.getRestClient()
                           .requestGet(this.baseUrl + "/ligands", Ligands.class);
            }

            @Override
            public Comments getLigandComments(Long ligandId)
            {
                return this.getRestClient()
                           .requestGet(this.baseUrl + "/ligands/" + ligandId + "/comments", Comments.class);
            }
        };
    }

}
