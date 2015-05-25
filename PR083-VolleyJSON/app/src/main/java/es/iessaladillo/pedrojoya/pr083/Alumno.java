package es.iessaladillo.pedrojoya.pr083;

// Clase para modelar el alumno.
class Alumno {

    // Constantes.
    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_DIRECCION = "direccion";
    public static final String KEY_TELEFONO = "telefono";
    public static final String KEY_CURSO = "curso";
    public static final String KEY_REPETIDOR = "repetidor";
    public static final String KEY_EDAD = "edad";
    public static final String KEY_FOTO = "foto";

    // Variables miembro.
    private String foto;
    private String nombre;
    private int edad;
    private String curso;
    private String direccion;
    private String telefono;
    private boolean repetidor;

    public String getFoto() {
        return foto;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getCurso() {
        return curso;
    }

    public boolean isRepetidor() {
        return repetidor;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRepetidor(boolean repetidor) {
        this.repetidor = repetidor;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}