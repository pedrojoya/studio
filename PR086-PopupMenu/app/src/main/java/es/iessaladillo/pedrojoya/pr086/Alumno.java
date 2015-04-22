package es.iessaladillo.pedrojoya.pr086;

class Alumno {

    // Propiedades.
    private final String nombre;
    private final String direccion;
    private final String telefono;
    private final String curso;

    // Constructor.
    public Alumno(String nombre, String direccion, String telefono, String curso) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.curso = curso;
    }

    // Getters y setters.
    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCurso() {
        return curso;
    }

}
