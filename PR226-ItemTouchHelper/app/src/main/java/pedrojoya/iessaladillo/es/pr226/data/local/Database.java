package pedrojoya.iessaladillo.es.pr226.data.local;

import com.mooveit.library.Fakeit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pedrojoya.iessaladillo.es.pr226.data.local.model.Student;

public class Database {

    private static final String BASE_URL = "https://picsum.photos/100/100?image=";

    private static Database instance;

    private final ArrayList<Student> students;
    private final Random random = new Random();

    private Database() {
        students = new ArrayList<>();
        insertInitialData();
    }

    public synchronized static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private void insertInitialData() {
        for (int i = 0; i < 5; i++) {
            addFakeStudent();
        }
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addFakeStudent() {
        Student student = new Student(Fakeit.name().name(), Fakeit.address().streetAddress(),
                BASE_URL + random.nextInt(1084));
        students.add(student);
    }

    public void deleteStudent(int position) {
        students.remove(position);
    }

}