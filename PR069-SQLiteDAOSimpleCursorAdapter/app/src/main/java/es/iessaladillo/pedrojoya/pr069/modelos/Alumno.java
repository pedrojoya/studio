package es.iessaladillo.pedrojoya.pr069.modelos;

public class Alumno {

    private long id;
    private String nombre;
    private String telefono;
    private String curso;
    private String direccion;

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
