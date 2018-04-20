package pedrojoya.iessaladillo.es.pr229.data.local;

import com.mooveit.library.Fakeit;

import java.util.ArrayList;
import java.util.Random;

import pedrojoya.iessaladillo.es.pr229.data.model.Student;

@SuppressWarnings("unused")
public class Database {

    private static Database instance;

    private final ArrayList<Student> students;
    private int autonumeric = 1;
    private Random aleatorio = new Random();

    private Database() {
        // Create initial students.
        students = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            students.add(createStudent());
        }
    }

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }


    public ArrayList<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
/*
        boolean deleted = false;
        for (int i = 0; i < students.size() && !deleted; i++) {
            if (students.get(i).getId() == student.getId()) {
                students.remove(i);
                deleted = true;
            }
        }
*/
    students.remove(student);
    }

    public Student createStudent() {
        int num = autonumeric++;
        return new Student(num, Fakeit.name().name(), Fakeit.address().streetAddress(),
                "http://lorempixel.com/100/100/abstract/" + (num % 10 + 1) + "/");
    }

    public void updateStudent(Student student, Student newStudent) {
        int index = students.indexOf(student);
        if (index >= 0) {
            students.set(index, newStudent);
        }
    }
}
