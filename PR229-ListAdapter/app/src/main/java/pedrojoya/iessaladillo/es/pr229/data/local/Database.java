package pedrojoya.iessaladillo.es.pr229.data.local;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.mooveit.library.Fakeit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pedrojoya.iessaladillo.es.pr229.data.local.model.Student;

@SuppressWarnings("unused")
public class Database {

    private static final String BASE_URL = "https://picsum.photos/100/100?image=";

    private static Database instance;

    private final ArrayList<Student> students = new ArrayList<>();
    private final Random random = new Random();
    private final MutableLiveData<List<Student>> studentsLiveData = new MutableLiveData<>();
    private int autonumeric = 1;

    private Database() {
        // Create initial students.
        for (int i = 0; i < 5; i++) {
            students.add(newFakeStudent());
        }
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

    public LiveData<List<Student>> queryStudents() {
        studentsLiveData.setValue(new ArrayList<>(students));
        return studentsLiveData;
    }

    public void addStudent(Student student) {
        students.add(student);
        studentsLiveData.setValue(new ArrayList<>(students));
    }

    public void deleteStudent(Student student) {
        students.remove(student);
        studentsLiveData.setValue(new ArrayList<>(students));
    }

    public void updateStudent(Student student, Student newStudent) {
        int index = students.indexOf(student);
        if (index >= 0) {
            students.set(index, newStudent);
        }
        studentsLiveData.setValue(new ArrayList<>(students));
    }

    public Student newFakeStudent() {
        int num = autonumeric++;
        return new Student(num, Fakeit.name().name(), Fakeit.address().streetAddress(),
                BASE_URL + random.nextInt(1084));
    }

}
