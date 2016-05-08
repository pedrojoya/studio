package es.iessaladillo.pedrojoya.pr140.data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;

@SuppressWarnings("unused")
public class Resultados
{
    @Element
    private String numero_partidos;
    @ElementList
    private List<Partido> partidos;

    public String getNumero_partidos ()
    {
        return numero_partidos;
    }

    public void setNumero_partidos (String numero_partidos)
    {
        this.numero_partidos = numero_partidos;
    }

    public List<Partido> getPartidos ()
    {
        return partidos;
    }

    public void setPartidos (List<Partido> partido)
    {
        this.partidos = partido;
    }

}