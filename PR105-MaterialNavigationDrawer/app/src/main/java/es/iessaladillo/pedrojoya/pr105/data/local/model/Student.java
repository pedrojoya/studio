package es.iessaladillo.pedrojoya.pr105.data.local.model;

// Modelo Student.
public class Student {

    private final String nombre;
    private final String direccion;
    private final String urlFoto;

    @SuppressWarnings("UnusedParameters")
    public Student(String nombre, String direccion, int edad, String urlFoto) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.urlFoto = urlFoto;

    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getNombre() {
        return nombre;
    }

}
