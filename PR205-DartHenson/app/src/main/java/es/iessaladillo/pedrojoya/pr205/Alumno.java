package es.iessaladillo.pedrojoya.pr205;

import com.f2prateek.dart.InjectExtra;

@SuppressWarnings({"WeakerAccess", "CanBeFinal"})
class Alumno {

    public static final int DEFAULT_EDAD = 18;
    public static final int MAX_EDAD = 130;

    @InjectExtra
    String nombre;
    @InjectExtra
    int edad;

    @SuppressWarnings("SameParameterValue")
    public Alumno(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

}
