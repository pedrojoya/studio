package es.iessaladillo.pedrojoya.pr153;

import java.util.ArrayList;
import java.util.Random;

// Simula un BD.
@SuppressWarnings("unused")
class DB {

    private static DB db;

    private DB() {
        datos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            datos.add(getNextAlumno());
        }
    }

    public static DB getInstance() {
        if (db == null) {
            db = new DB();
        }
        return db;
    }

    // Lista de alumnos.
    private final ArrayList<Alumno> datos;
    private int next = 1;
    private final Random aleatorio = new Random();

    // Retorna la lista de alumnos.
    public ArrayList<Alumno> getAlumnos() {
        return datos;
    }

    // Agrega un alumno a la lista.
    public void addAlumno(Alumno alumno) {
        datos.add(alumno);
    }

    // Elimina un alumno de la lista.
    public void removeAlumno(int position) {
        datos.remove(position);
    }

    public int getAlumnosCount() {
        return datos.size();
    }

    private int getNext() {
        return next++;
    }

    public Alumno getNextAlumno() {
        int num = next++;
        return new Alumno("Alumno " + num, "c/ Su casa, nº " + num, aleatorio.nextInt(9) + 20,
                "http://lorempixel.com/100/100/abstract/" + (num % 10 + 1) + "/");
    }

}
