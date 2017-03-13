package es.iessaladillo.pedrojoya.pr140.data;

import org.simpleframework.xml.Element;

@SuppressWarnings("unused")
public class Abstenciones {
    @Element
    private String porcentaje;
    @Element
    private String cantidad;

    public String getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(String porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

}
