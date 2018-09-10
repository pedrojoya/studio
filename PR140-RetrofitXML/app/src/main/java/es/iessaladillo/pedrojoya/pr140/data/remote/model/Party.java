package es.iessaladillo.pedrojoya.pr140.data.remote.model;

import android.graphics.Color;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@SuppressWarnings("unused")
@Root(name = "partido")
public class Party {

    @Element(name = "nombre")
    private String name;
    @Element(name = "votos_porciento")
    private String percentageOfVotes;
    @Element(name = "id_partido")
    private String partyId;
    @Element(name = "electos")
    private String elected;
    @Element(name = "votos_numero")
    private String numberOfVotes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPercentageOfVotes() {
        return percentageOfVotes;
    }

    public void setPercentageOfVotes(String percentageOfVotes) {
        this.percentageOfVotes = percentageOfVotes;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getElected() {
        return elected;
    }

    public void setElected(String elected) {
        this.elected = elected;
    }

    public String getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(String numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    public int getColor() {
        switch (partyId) {
            case "4252": // PP
                return Color.argb(255, 0, 163, 223);
            case "4327": // PSOE
            case "4333":
            case "4330":
                return Color.argb(255, 239, 25, 32);
            case "1456": // Cs
                return Color.argb(255, 239, 122, 54);
            case "3465": // IU
                return Color.argb(255, 219, 5, 37);
            case "131": // Podemos
            case "271":
            case "3782":
            case "4694":
                return Color.argb(255, 96, 44, 97);
            case "4100": // PA
            case "4099":
                return Color.argb(255, 27, 168, 56);
            case "2944": // PI Los Barrios
                return Color.argb(255, 242, 180, 7);
            default:
                return Color.GRAY;
        }
    }

}
