package es.iessaladillo.pedrojoya.pr140.data;

import org.simpleframework.xml.Element;

@SuppressWarnings("unused")
public class Partido {

    @Element
    private String nombre;
    @Element
    private String votos_porciento;
    @Element
    private String id_partido;
    @Element
    private String electos;
    @Element
    private String votos_numero;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getVotos_porciento() {
        return votos_porciento;
    }

    public void setVotos_porciento(String votos_porciento) {
        this.votos_porciento = votos_porciento;
    }

    public String getId_partido() {
        return id_partido;
    }

    public void setId_partido(String id_partido) {
        this.id_partido = id_partido;
    }

    public String getElectos() {
        return electos;
    }

    public void setElectos(String electos) {
        this.electos = electos;
    }

    public String getVotos_numero() {
        return votos_numero;
    }

    public void setVotos_numero(String votos_numero) {
        this.votos_numero = votos_numero;
    }

}
