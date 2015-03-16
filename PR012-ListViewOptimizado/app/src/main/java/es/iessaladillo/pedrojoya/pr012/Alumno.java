package es.iessaladillo.pedrojoya.pr012;

// Clase para modelar el alumno.
class Alumno {
    // Variables miembro.
    private int foto;
    private String nombre;
    private int edad;
    private String ciclo;
    private String curso;
    private boolean repetidor;

    // Constructor.
    public Alumno(int foto, String nombre, int edad, String ciclo,
            String curso, boolean repetidor) {
        this.foto = foto;
        this.nombre = nombre;
        this.edad = edad;
        this.ciclo = ciclo;
        this.curso = curso;
        this.repetidor = repetidor;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public boolean isRepetidor() {
        return repetidor;
    }

    public void setRepetidor(boolean repetidor) {
        this.repetidor = repetidor;
    }

}
