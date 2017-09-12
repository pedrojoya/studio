package es.iessaladillo.pedrojoya.pr095.data.model;

public class Song {

    // Propieadades.
    private final String nombre;
    private final String duracion;
    private final String autor;
    private final String url;

    // Constructor.
    public Song(String nombre, String duracion, String autor, String url) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.autor = autor;
        this.url = url;
    }

    // Getters.
    public String getNombre() {
        return nombre;
    }

    public String getDuracion() {
        return duracion;
    }

    public String getAutor() {
        return autor;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return nombre + " (" + duracion + ")";
    }

}
