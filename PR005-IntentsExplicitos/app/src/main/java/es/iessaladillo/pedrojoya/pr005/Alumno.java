package es.iessaladillo.pedrojoya.pr005;

class Alumno {

    public static final int DEFAULT_EDAD = 18;

    private String nombre;
    private int edad;

    public Alumno() {
        edad = DEFAULT_EDAD;
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

}
