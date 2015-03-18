package es.iessaladillo.pedrojoya.pr086;

class Alumno {

    // Propiedades.
    private String nombre;
    private String direccion;
    private String telefono;
    private String curso;

    // Constructor.
    public Alumno(String nombre, String direccion, String telefono, String curso) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.curso = curso;
    }

    public Alumno() {
    }

    // Getters y setters.
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

}
