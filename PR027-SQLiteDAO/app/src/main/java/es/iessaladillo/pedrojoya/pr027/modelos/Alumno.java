package es.iessaladillo.pedrojoya.pr027.modelos;

public class Alumno {

    long id;
    String nombre;
    String telefono;
    String curso;
    String direccion;

    Alumno(long id, String nombre, String telefono, String curso,
            String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.curso = curso;
        this.direccion = direccion;
    }

    public Alumno(String nombre, String telefono, String curso, String direccion) {
        this.id = 0;
        this.nombre = nombre;
        this.telefono = telefono;
        this.curso = curso;
        this.direccion = direccion;
    }

    public Alumno() {
        // Establezo los valores iniciales para las propiedades
        this.id = 0;
        this.nombre = null;
        this.telefono = null;
        this.curso = null;
        this.direccion = null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    // Getters y Setters de las propiedades.
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
