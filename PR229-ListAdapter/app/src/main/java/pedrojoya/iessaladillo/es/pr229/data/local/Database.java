package pedrojoya.iessaladillo.es.pr229.data.local;

import com.mooveit.library.Fakeit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import pedrojoya.iessaladillo.es.pr229.data.local.model.Student;

@SuppressWarnings("unused")
public class Database {

    private static final String BASE_URL = "https://picsum.photos/100/100?image=";

    private static volatile Database instance;
    @NonNull
    private static final Random random = new Random();
    @NonNull
    private final ArrayList<Student> students = new ArrayList<>();
    @NonNull
    private final MutableLiveData<List<Student>> studentsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Student>> studentsLiveDataDesc = new MutableLiveData<>();
    private int studentsAutoId = 1;

    private Database() {
        updateLiveDatas();
        insertInitialData();
    }

    private void updateLiveDatas() {
        studentsLiveData.postValue(orderByName(students, false));
        studentsLiveDataDesc.postValue(orderByName(students, true));
    }

    private List<Student> orderByName(List<Student> students, boolean desc) {
        List<Student> orderedList = new ArrayList<>(students);
        Collections.sort(orderedList,
            (student1, student2) -> (desc ? -1 : 1) * student1.getName().compareTo
                (student2.getName()));
        return orderedList;
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
    public LiveData<List<Student>> queryStudentsOrderedByName(boolean desc) {
        return desc ? studentsLiveDataDesc : studentsLiveData;
    }

    public synchronized void insertStudent(@NonNull Student student) {
        student.setId(++studentsAutoId);
        students.add(student);
        updateLiveDatas();
    }

    public synchronized void deleteStudent(@NonNull Student student) {
        students.remove(student);
        updateLiveDatas();
    }

    public synchronized void updateStudent(@NonNull Student student, Student newStudent) {
        int index = students.indexOf(student);
        if (index >= 0) {
            students.set(index, newStudent);
        }
        updateLiveDatas();
    }

    @NonNull
    public static Student newFakeStudent() {
        return new Student(0, Fakeit.name().name(), Fakeit.address().streetAddress(),
            BASE_URL + random.nextInt(1084));
    }

}
