package es.iessaladillo.pedrojoya.pr093.modelos;

public class Cancion {

    // Propieadades.
    String nombre;
    String duracion;
    String url;

    // Constructor.
    public Cancion(String nombre, String duracion, String url) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.url = url;
    }

    // Getters.
    public String getNombre() {
        return nombre;
    }

    public String getDuracion() {
        return duracion;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return nombre + " (" + duracion + ")";
    }

}
