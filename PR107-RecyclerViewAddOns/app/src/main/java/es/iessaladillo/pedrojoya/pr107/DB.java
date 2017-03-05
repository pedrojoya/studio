package es.iessaladillo.pedrojoya.pr107;

import java.util.ArrayList;

@SuppressWarnings("unused")
class DB {

    private static DB db;

    private final ArrayList<Alumno> datos;
    private int next = 1;

    public static DB getInstance() {
        if (db == null) {
            db = new DB();
        }
        return db;
    }

    private DB() {
        datos = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            datos.add(getNextAlumno());
        }
    }

    public ArrayList<Alumno> getAlumnos() {
        return datos;
    }

    public void addAlumno(Alumno alumno) {
        datos.add(alumno);
    }

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
        return new Alumno(num, "Alumno " + num, "c/ Su casa, nº " + num,
                "http://lorempixel.com/100/100/abstract/" + (num % 10 + 1) + "/");
    }

}
