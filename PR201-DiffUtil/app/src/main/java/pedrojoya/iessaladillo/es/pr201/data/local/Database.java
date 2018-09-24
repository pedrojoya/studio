package pedrojoya.iessaladillo.es.pr201.data.local;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mooveit.library.Fakeit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pedrojoya.iessaladillo.es.pr201.data.local.model.Student;

public class Database {

    private static final String BASE_URL = "https://picsum.photos/100/100?image=";

    private static Database instance;
    private static final Random random = new Random();

    private final ArrayList<Student> students = new ArrayList<>();
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

    public void insertStudent(Student student) {
        student.setId(++studentsAutoId);
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

    public static Student newFakeStudent() {
        return new Student(0, Fakeit.name().name(), Fakeit.address().streetAddress(),
                BASE_URL + random.nextInt(1084));
    }

}
