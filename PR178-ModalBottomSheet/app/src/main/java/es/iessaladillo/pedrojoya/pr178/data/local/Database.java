package es.iessaladillo.pedrojoya.pr178.data.local;

import com.mooveit.library.Fakeit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr178.data.local.model.Student;

public class Database {

    private static final String BASE_URL = "https://picsum.photos/100/100?image=";

    private static Database instance;
    private final static Random random = new Random();

    private final ArrayList<Student> students = new ArrayList<>();
    private final MutableLiveData<List<Student>> studentsLiveData = new MutableLiveData<>();
    private long studentsAutoId;

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
        return studentsLiveData;
    }

    public void insertStudent(Student student) {
        student.setId(++studentsAutoId);
        students.add(student);
        updateLiveData();
    }

    private void updateLiveData() {
        ArrayList<Student> newList = new ArrayList<>(students);
        Collections.sort(newList, (student1, student2) -> student1.getName().compareTo(student2.getName()));
        studentsLiveData.setValue(newList);
    }

    public void deleteStudent(Student student) {
        students.remove(student);
        updateLiveData();
    }

    public static Student newFakeStudent() {
        return new Student(0, Fakeit.name().name(), Fakeit.address().streetAddress(),
                BASE_URL + random.nextInt(1084));
    }

}
