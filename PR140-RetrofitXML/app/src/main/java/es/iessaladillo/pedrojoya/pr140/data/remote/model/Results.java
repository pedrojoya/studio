package es.iessaladillo.pedrojoya.pr140.data.remote.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@SuppressWarnings("unused")
@Root(name = "resultados")
public class Results {
    @Element(name = "numero_partidos")
    private String numberOfParties;
    @ElementList(name = "partidos")
    private List<Party> parties;

    public String getNumberOfParties() {
        return numberOfParties;
    }

    public void setNumberOfParties(String numberOfParties) {
        this.numberOfParties = numberOfParties;
    }

    public List<Party> getParties() {
        return parties;
    }

    public void setParties(List<Party> party) {
        this.parties = party;
    }

}
