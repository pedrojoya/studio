package pedrojoya.iessaladillo.es.pr106.data.local;

import com.mooveit.library.Fakeit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import pedrojoya.iessaladillo.es.pr106.data.local.model.Student;

public class Database {

    private static final String BASE_URL = "https://picsum.photos/200/300?image=";

    private static volatile Database instance;
    @NonNull
    private static final Random random = new Random();

    @NonNull
    private final ArrayList<Student> students = new ArrayList<>();
    private long studentsAutoId;

    private Database() {
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
            insertStudent(newFakeStudent());
        }
    }

    @NonNull
    public List<Student> queryStudents() {
        ArrayList<Student> sortedList = new ArrayList<>(students);
        Collections.sort(sortedList,
                (student1, student2) -> student1.getName().compareTo(student2.getName()));
        return sortedList;
    }

    public synchronized void insertStudent(@NonNull Student student) {
            student.setId(++studentsAutoId);
            students.add(student);
    }

    public synchronized void deleteStudent(@NonNull Student student) {
        students.remove(student);
    }

    @NonNull
    public static Student newFakeStudent() {
        return new Student(Fakeit.name().name(), Fakeit.address().streetAddress(),
                BASE_URL + random.nextInt(1084));
    }

}
