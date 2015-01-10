package pedrojoya.iessaladillo.es.pr106;

import java.util.ArrayList;

// Simula un BD.
public class DB {

    // Lista de alumnos.
    private static ArrayList<Alumno> datos;
    private static int next = 1;

    // Inicialización.
    static {
        datos = new ArrayList<Alumno>();
        for (int i = 0; i < 5; i++) {
            datos.add(new Alumno("Alumno " + (next++)));
        }
    }

    // Retorna la lista de alumnos.
    public static ArrayList<Alumno> getAlumnos() {
        return datos;
    }

    // Agrega un alumno a la lista.
    public static void addAlumno(Alumno alumno) {
        datos.add(alumno);
    }

    // Elimina un alumno de la lista.
    public static void removeAlumno(int position) {
        datos.remove(position);
    }

    public static int getAlumnosCount() {
        return datos.size();
    }

    public static int getNext() {
        return next++;
    }

}
