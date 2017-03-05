package es.iessaladillo.pedrojoya.pr118.datos;

import android.provider.BaseColumns;

public class InstitutoContract {

    // Constantes generales de la BD.
    public static final String BD_NOMBRE = "instituto";
    public static final int BD_VERSION = 1;

    // Tabla Alumno.
    public abstract static class Alumno implements BaseColumns {
        public static final String TABLA = "alumnos";
        public static final String NOMBRE = "nombre";
        public static final String CURSO = "curso";
        public static final String TELEFONO = "telefono";
        public static final String DIRECCION = "direccion";
        public static final String[] TODOS = new String[]{_ID, NOMBRE, CURSO, TELEFONO, DIRECCION};
    }

    // Constructor privado para que NO pueda instanciarse.
    private InstitutoContract() {
    }

}
