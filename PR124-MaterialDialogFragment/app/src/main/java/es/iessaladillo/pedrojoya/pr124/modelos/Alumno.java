package es.iessaladillo.pedrojoya.pr124.modelos;

// Modelo Alumno.
public class Alumno {

    private String nombre;
    private String direccion;
    private int edad;
    private String urlFoto;

    public Alumno(String nombre, String direccion, int edad, String urlFoto) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.edad = edad;
        this.urlFoto = urlFoto;

    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
