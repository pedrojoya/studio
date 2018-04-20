package es.iessaladillo.pedrojoya.pr105.data;

import java.util.ArrayList;
import java.util.Random;

import es.iessaladillo.pedrojoya.pr105.data.local.model.Student;

@SuppressWarnings("unused")
public class DB {

    private static final ArrayList<Student> students;
    private static int next = 1;
    private static final Random aleatorio = new Random();

    static {
        students = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            students.add(getNextAlumno());
        }
    }

    private DB() { }

    // Retorna la lista de alumnos.
    public static ArrayList<Student> getAlumnos() {
        return students;
    }

    // Agrega un student a la lista.
    public static void addAlumno(Student student) {
        students.add(student);
    }

    // Elimina un alumno de la lista.
    public static void removeAlumno(int position) {
        students.remove(position);
    }

    public static int getAlumnosCount() {
        return students.size();
    }

    private static int getNext() {
        return next++;
    }

    private static Student getNextAlumno() {
        int num = next++;
        return new Student("Student " + num, "c/ Su casa, nº " + num, aleatorio.nextInt(9) + 20,
                "http://lorempixel.com/100/100/abstract/" + (num % 10 + 1) + "/");
    }

}
