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

import org.apache.commons.lang.StringUtils;

public class DatabaseLink
{
    private String accession;
    private String database;
    private String url;
    private String species;

    public static enum Database
    {
        UNIPROT("UniProtKB"), ENSEMBL("Ensembl Gene"), CHEMBL("ChEMBL"), DRUGBANK("DrugBank"), GPCRDB("GPCRDB");

        private String matchStr;

        private Database(String matchStr)
        {
            this.matchStr = matchStr;
        }

        public boolean matches(String database)
        {
            return StringUtils.containsIgnoreCase(database, this.matchStr);
        }
    }

    public boolean hasDatabase(Database database)
    {
        return database.matches(this.database);
    }

    public String getAccession()
    {
        return this.accession;
    }

    public void setAccession(String accession)
    {
        this.accession = accession;
    }

    public String getDatabase()
    {
        return this.database;
    }

    public void setDatabase(String database)
    {
        this.database = database;
    }

    public String getUrl()
    {
        return this.url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getSpecies()
    {
        return this.species;
    }

    public void setSpecies(String species)
    {
        this.species = species;
    }

    public boolean hasSpeciesType(SpeciesType speciesType)
    {
        return speciesType.matches(this.species);
    }

}
