package pedrojoya.iessaladillo.es.pr225.data.local;

import com.mooveit.library.Fakeit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import pedrojoya.iessaladillo.es.pr225.data.local.model.Student;

public class Database {

    private static final String BASE_URL = "https://picsum.photos/100/100?image=";
    private static volatile Database instance;

    @NonNull
    private final ArrayList<Student> students = new ArrayList<>();
    private long studentsAutoId;
    @NonNull
    private final Random random = new Random();

    private Database() {
        insertInitialData();
    }

    @NonNull
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
        return new ArrayList<>(students);
    }

    @SuppressWarnings("WeakerAccess")
    public synchronized void insertStudent(@NonNull Student student) {
        student.setId(++studentsAutoId);
        students.add(student);
    }

    @SuppressWarnings("WeakerAccess")
    @NonNull
    public Student newFakeStudent() {
        return new Student(Fakeit.name().name(), Fakeit.address().streetAddress(),
                BASE_URL + random.nextInt(180));
    }

}
