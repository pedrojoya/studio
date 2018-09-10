package es.iessaladillo.pedrojoya.pr140.data.remote.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@SuppressWarnings("unused")
@Root(name = "escrutinio_sitio")
public class Counting {
    @Element(name = "resultados")
    private Results results;
    @Element(name = "num_a_elegir")
    private String seats;
    @Element(name = "nombre_lugar")
    private String place;
    @Element(name = "porciento_escrutado")
    private String counted_percentage;
    @Element(name = "ts")
    private String ts;
    @Element(name = "nombre_disputado")
    private String seatName;
    @Element(name = "tipo_sitio")
    private String tipo_sitio;
    @Element(name = "nombre_sitio")
    private String placeType;
    @Element(name = "votos")
    private Votes votes;

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCounted_percentage() {
        return counted_percentage;
    }

    public void setCounted_percentage(String counted_percentage) {
        this.counted_percentage = counted_percentage;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public String getTipo_sitio() {
        return tipo_sitio;
    }

    public void setTipo_sitio(String tipo_sitio) {
        this.tipo_sitio = tipo_sitio;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public Votes getVotes() {
        return votes;
    }

    public void setVotes(Votes votes) {
        this.votes = votes;
    }

}