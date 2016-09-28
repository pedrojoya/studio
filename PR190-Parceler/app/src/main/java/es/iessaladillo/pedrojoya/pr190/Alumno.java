package es.iessaladillo.pedrojoya.pr190;

import org.parceler.Parcel;

@SuppressWarnings("WeakerAccess")
@Parcel
class Alumno {

    public static final int DEFAULT_EDAD = 18;

    String nombre;
    int edad;

    @SuppressWarnings("unused")
    public Alumno() {
    }

    @SuppressWarnings("SameParameterValue")
    public Alumno(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
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
