package es.iessaladillo.pedrojoya.pr167.bd;

import android.provider.BaseColumns;

public class Instituto {

	// Constantes generales de la BD.
	public static final String BD_NOMBRE = "instituto";
	public static final int BD_VERSION = 1;
    public static final String PROVIDER_NAME = "InstitutoProvider";
    public static final String PROVIDER_AUTHORITY = "es.iessaladillo.pedrojoya.pr167.authority";

    // Tabla Alumno.
	public static abstract class Alumno implements BaseColumns {
		public static final String TABLA = "alumnos";
		public static final String NOMBRE = "nombre";
		public static final String CURSO = "curso";
		public static final String TELEFONO = "telefono";
		public static final String DIRECCION = "direccion";
		public static final String[] TODOS = new String[] { _ID, NOMBRE, CURSO,
				TELEFONO, DIRECCION };
	}

	// Tabla Cursos.
	public static abstract class Curso implements BaseColumns {
		public static final String TABLA = "cursos";
		public static final String NOMBRE = "nombre";
	}

	// Constructor privado para que NO pueda instanciarse.
	private Instituto() {
	}

}
