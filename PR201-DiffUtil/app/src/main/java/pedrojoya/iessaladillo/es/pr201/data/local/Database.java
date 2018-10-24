package pedrojoya.iessaladillo.es.pr201.data.local;

import com.mooveit.library.Fakeit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import pedrojoya.iessaladillo.es.pr201.data.local.model.Student;

public class Database {

    private static final String BASE_URL = "https://picsum.photos/100/100?image=";

    private static volatile Database instance;
    @NonNull
    private static final Random random = new Random();
    @NonNull
    private final ArrayList<Student> students = new ArrayList<>();
    @NonNull
    private final MutableLiveData<List<Student>> studentsLiveData = new MutableLiveData<>();
    private int studentsAutoId = 1;

    private Database() {
        insertInitialData();
    }

    private void insertInitialData() {
        for (int i = 0; i < 5; i++) {
            insertStudent(newFakeStudent());
        }
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

    @NonNull
    public LiveData<List<Student>> queryStudents() {
        studentsLiveData.setValue(new ArrayList<>(students));
        return studentsLiveData;
    }

    public synchronized void insertStudent(@NonNull Student student) {
        student.setId(++studentsAutoId);
        students.add(student);
        studentsLiveData.setValue(new ArrayList<>(students));
    }

    public synchronized void deleteStudent(@NonNull Student student) {
        students.remove(student);
        studentsLiveData.setValue(new ArrayList<>(students));
    }

    public synchronized void updateStudent(@NonNull Student student, Student newStudent) {
        int index = students.indexOf(student);
        if (index >= 0) {
            students.set(index, newStudent);
        }
        studentsLiveData.setValue(new ArrayList<>(students));
    }

    @NonNull
    public static Student newFakeStudent() {
        return new Student(0, Fakeit.name().name(), Fakeit.address().streetAddress(),
                BASE_URL + random.nextInt(1084));
    }

}
