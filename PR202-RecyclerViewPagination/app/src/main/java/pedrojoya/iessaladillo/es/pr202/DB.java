package pedrojoya.iessaladillo.es.pr202;

import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("unused")
class DB {

    private static DB db;
    private final ArrayList<Alumno> datos;
    private int next = 1;
    private Random aleatorio = new Random();

    private DB() {
        datos = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            datos.add(getNextAlumno());
        }
    }

    public static DB getInstance() {
        if (db == null) {
            db = new DB();
        }
        return db;
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
        return new Alumno("Alumno " + num, "c/ Su casa, nº " + num,
                "http://lorempixel.com/100/100/abstract/" + (num % 10 + 1) + "/");
    }

    public ArrayList<Alumno> getPage(int page) {
        ArrayList<Alumno> lista = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            lista.add(datos.get(page * 10 + i));
        }
        return lista;
    }
}
