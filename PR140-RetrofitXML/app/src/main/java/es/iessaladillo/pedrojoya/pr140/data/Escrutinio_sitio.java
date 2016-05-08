package es.iessaladillo.pedrojoya.pr140.data;

import org.simpleframework.xml.Element;

@SuppressWarnings("unused")
public class Escrutinio_sitio
{
    @Element
    private Resultados resultados;
    @Element
    private String num_a_elegir;
    @Element
    private String nombre_lugar;
    @Element
    private String porciento_escrutado;
    @Element
    private String ts;
    @Element
    private String nombre_disputado;
    @Element
    private String tipo_sitio;
    @Element
    private String nombre_sitio;
    @Element
    private Votos votos;

    public Resultados getResultados ()
    {
        return resultados;
    }

    public void setResultados (Resultados resultados)
    {
        this.resultados = resultados;
    }

    public String getNum_a_elegir ()
    {
        return num_a_elegir;
    }

    public void setNum_a_elegir (String num_a_elegir)
    {
        this.num_a_elegir = num_a_elegir;
    }

    public String getNombre_lugar ()
    {
        return nombre_lugar;
    }

    public void setNombre_lugar (String nombre_lugar)
    {
        this.nombre_lugar = nombre_lugar;
    }

    public String getPorciento_escrutado ()
    {
        return porciento_escrutado;
    }

    public void setPorciento_escrutado (String porciento_escrutado)
    {
        this.porciento_escrutado = porciento_escrutado;
    }

    public String getTs ()
    {
        return ts;
    }

    public void setTs (String ts)
    {
        this.ts = ts;
    }

    public String getNombre_disputado ()
    {
        return nombre_disputado;
    }

    public void setNombre_disputado (String nombre_disputado)
    {
        this.nombre_disputado = nombre_disputado;
    }

    public String getTipo_sitio ()
    {
        return tipo_sitio;
    }

    public void setTipo_sitio (String tipo_sitio)
    {
        this.tipo_sitio = tipo_sitio;
    }

    public String getNombre_sitio ()
    {
        return nombre_sitio;
    }

    public void setNombre_sitio (String nombre_sitio)
    {
        this.nombre_sitio = nombre_sitio;
    }

    public Votos getVotos ()
    {
        return votos;
    }

    public void setVotos (Votos votos)
    {
        this.votos = votos;
    }

}