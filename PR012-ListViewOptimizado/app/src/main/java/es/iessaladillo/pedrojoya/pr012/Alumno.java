package es.iessaladillo.pedrojoya.pr012;

// Clase para modelar el alumno.
class Alumno {
    // Variables miembro.
    private final int foto;
    private final String nombre;
    private final int edad;
    private final String ciclo;
    private final String curso;
    private final boolean repetidor;

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

    public boolean isRepetidor() {
        return repetidor;
    }

}
