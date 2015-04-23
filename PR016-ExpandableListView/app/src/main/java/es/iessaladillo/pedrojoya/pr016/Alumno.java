package es.iessaladillo.pedrojoya.pr016;

// Clase para modelar el Alumno.
class Alumno {

    // Variables miembro.
    private final String nombre;
    private final int edad;
    private final String ciclo;
    private final String curso;

    // Constructores.
    public Alumno(String nombre, int edad, String ciclo, String curso) {
        this.nombre = nombre;
        this.edad = edad;
        this.ciclo = ciclo;
        this.curso = curso;
    }

    // Getters and Setters.
    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getCiclo() {
        return ciclo;
    }

    public String getCurso() {
        return curso;
    }

}