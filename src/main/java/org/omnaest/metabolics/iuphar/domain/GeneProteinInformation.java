package org.omnaest.metabolics.iuphar.domain;

public class GeneProteinInformation
{
    private long   targetId;
    private String species;
    private String geneSymbol;
    private String geneName;
    private String officialGeneId;
    private String genomicLocation;
    private String aminoAcids;
    private String transmembraneDomains;
    private String poreLoops;

    private References refs;

    public boolean hasSpeciesType(SpeciesType speciesType)
    {
        return speciesType.matches(this.species);
    }

    public long getTargetId()
    {
        return this.targetId;
    }

    public void setTargetId(long targetId)
    {
        this.targetId = targetId;
    }

    public String getSpecies()
    {
        return this.species;
    }

    public void setSpecies(String species)
    {
        this.species = species;
    }

    public String getGeneSymbol()
    {
        return this.geneSymbol;
    }

    public void setGeneSymbol(String geneSymbol)
    {
        this.geneSymbol = geneSymbol;
    }

    public String getGeneName()
    {
        return this.geneName;
    }

    public void setGeneName(String geneName)
    {
        this.geneName = geneName;
    }

    public String getOfficialGeneId()
    {
        return this.officialGeneId;
    }

    public void setOfficialGeneId(String officialGeneId)
    {
        this.officialGeneId = officialGeneId;
    }

    public String getGenomicLocation()
    {
        return this.genomicLocation;
    }

    public void setGenomicLocation(String genomicLocation)
    {
        this.genomicLocation = genomicLocation;
    }

    public String getAminoAcids()
    {
        return this.aminoAcids;
    }

    public void setAminoAcids(String aminoAcids)
    {
        this.aminoAcids = aminoAcids;
    }

    public String getTransmembraneDomains()
    {
        return this.transmembraneDomains;
    }

    public void setTransmembraneDomains(String transmembraneDomains)
    {
        this.transmembraneDomains = transmembraneDomains;
    }

    public String getPoreLoops()
    {
        return this.poreLoops;
    }

    public void setPoreLoops(String poreLoops)
    {
        this.poreLoops = poreLoops;
    }

    public References getRefs()
    {
        return this.refs;
    }

    public void setRefs(References refs)
    {
        this.refs = refs;
    }

    @Override
    public String toString()
    {
        return "GeneProteinInformation [targetId=" + this.targetId + ", species=" + this.species + ", geneSymbol=" + this.geneSymbol + ", geneName="
                + this.geneName + ", officialGeneId=" + this.officialGeneId + ", genomicLocation=" + this.genomicLocation + ", aminoAcids=" + this.aminoAcids
                + ", transmembraneDomains=" + this.transmembraneDomains + ", poreLoops=" + this.poreLoops + ", refs=" + this.refs + "]";
    }

}
