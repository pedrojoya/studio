package es.iessaladillo.pedrojoya.pr107;

import java.util.ArrayList;

// Simula un BD.
class DB {

    // Lista de alumnos.
    private static final ArrayList<Alumno> datos;
    private static int next = 1;

    // Inicialización.
    static {
        datos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            datos.add(getNextAlumno());
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

    private static int getNext() {
        return next++;
    }

    public static Alumno getNextAlumno() {
        int num = next++;
        return new Alumno(
                num,
                "Alumno " + num,
                "c/ Su casa, nº " + num,
                "http://lorempixel.com/100/100/abstract/" + (num%10 + 1) + "/"
        );
    }

}
