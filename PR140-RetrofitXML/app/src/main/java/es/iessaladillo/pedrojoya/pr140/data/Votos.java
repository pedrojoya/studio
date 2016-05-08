package es.iessaladillo.pedrojoya.pr140.data;

import org.simpleframework.xml.Element;

@SuppressWarnings("unused")
public class Votos {

    @Element
    private Nulos nulos;
    @Element
    private Contabilizados contabilizados;
    @Element
    private Blancos blancos;
    @Element
    private Abstenciones abstenciones;

    public Nulos getNulos() {
        return nulos;
    }

    public void setNulos(Nulos nulos) {
        this.nulos = nulos;
    }

    public Contabilizados getContabilizados() {
        return contabilizados;
    }

    public void setContabilizados(Contabilizados contabilizados) {
        this.contabilizados = contabilizados;
    }

    public Blancos getBlancos() {
        return blancos;
    }

    public void setBlancos(Blancos blancos) {
        this.blancos = blancos;
    }

    public Abstenciones getAbstenciones() {
        return abstenciones;
    }

    public void setAbstenciones(Abstenciones abstenciones) {
        this.abstenciones = abstenciones;
    }

}