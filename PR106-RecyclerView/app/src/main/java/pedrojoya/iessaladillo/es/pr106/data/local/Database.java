package pedrojoya.iessaladillo.es.pr106.data.local;

import com.mooveit.library.Fakeit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pedrojoya.iessaladillo.es.pr106.data.local.model.Student;

public class Database {

    private static final String BASE_URL = "https://picsum.photos/100/100?image=";

    private static Database instance;

    private final ArrayList<Student> students;
    private static final Random random = new Random();

    private Database() {
        students = new ArrayList<>();
        insertInitialData();
    }

    public static Database getInstance() {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }
        return instance;
    }

    private void insertInitialData() {
        for (int i = 0; i < 5; i++) {
            addStudent(newFakeStudent());
        }
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void deleteStudent(int position) {
        students.remove(position);
    }

    public static Student newFakeStudent() {
        return new Student(Fakeit.name().name(), Fakeit.address().streetAddress(),
                BASE_URL + random.nextInt(1084));
    }


}
