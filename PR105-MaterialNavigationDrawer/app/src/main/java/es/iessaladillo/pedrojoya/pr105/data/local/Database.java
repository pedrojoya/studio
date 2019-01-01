package es.iessaladillo.pedrojoya.pr105.data.local;

import com.mooveit.library.Fakeit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr105.data.local.model.Student;


public class Database {

    private static final String BASE_URL = "https://picsum.photos/200/300?image=";

    private static Database instance;
    private static final Random random = new Random();

    private final ArrayList<Student> _students = new ArrayList<>();
    private final MutableLiveData<List<Student>> studentsLiveData = new MutableLiveData<>();
    private int studentsAutoId;

    private Database() {
        insertInitialData();
        updateLiveData();
    }

    private void updateLiveData() {
        ArrayList<Student> sortedList = new ArrayList<>(_students);
        Collections.sort(sortedList,
            (student1, student2) -> student1.getName().compareTo(student2.getName()));
        studentsLiveData.postValue(sortedList);
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
        for (int i = 0; i < 15; i++) {
            insertStudent(newFakeStudent());
        }
    }

    public LiveData<List<Student>> queryStudents() {
        return studentsLiveData;
    }

    private synchronized void insertStudent(Student student) {
        student.setId(++studentsAutoId);
        _students.add(student);
    }

    private Student newFakeStudent() {
        return new Student(Fakeit.name().name(), Fakeit.address().streetAddress(),
                BASE_URL + random.nextInt(1084));
    }

}
