package pedrojoya.iessaladillo.es.pr223.data.local;

import com.mooveit.library.Fakeit;

import java.util.ArrayList;

import pedrojoya.iessaladillo.es.pr223.data.model.Student;

public class Database {

    private static Database instance;

    private final ArrayList<Student> students;
    private int autonumeric = 1;

    private Database() {
        // Create initial students.
        students = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            students.add(createStudent());
        }
    }

    public synchronized static Database getInstance() {
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

    public Student createStudent() {
        int num = autonumeric++;
        return new Student(num, Fakeit.name().name(), Fakeit.address().streetAddress(),
                "http://lorempixel.com/100/100/abstract/" + (num % 10 + 1) + "/");
    }

    public void deleteStudent(Student student) {
        students.remove(student);
    }

}
