package pedrojoya.iessaladillo.es.pr226.data.local;

import com.mooveit.library.Fakeit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import pedrojoya.iessaladillo.es.pr226.data.local.model.Student;

public class Database {

    private static final String BASE_URL = "https://picsum.photos/100/100?image=";

    private static volatile Database instance;
    @NonNull
    private final static Random random = new Random();
    @NonNull
    private final ArrayList<Student> students = new ArrayList<>();
    @NonNull
    private final MutableLiveData<List<Student>> studentsLiveData = new MutableLiveData<>();
    private static long studentsAutoId;

    private Database() {
        insertInitialData();
        updateStudentsLiveData();
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
    public LiveData<List<Student>> queryStudentsSortedByOrder() {
        return studentsLiveData;
    }

    private List<Student> sortByOrder(List<Student> students) {
        List<Student> orderedList = new ArrayList<>(students);
        Collections.sort(orderedList,
            (student1, student2) -> (int) (student1.getOrder() - student2.getOrder()));
        return orderedList;
    }


    public synchronized void insertStudent(@NonNull Student student) {
        student.setId(++studentsAutoId);
        student.setOrder(students.size() + 1);
        students.add(student);
        updateStudentsLiveData();
    }

    public synchronized void deleteStudent(@NonNull Student student) {
        students.remove(student);
        updateStudentsLiveData();
    }

    private void updateStudentsLiveData() {
        List<Student> ordered = sortByOrder(students);
        for (int i = 0; i < ordered.size(); i++) {
            ordered.get(i).setOrder(i+1);
        }
        studentsLiveData.setValue(ordered);
    }

    @NonNull
    public static Student newFakeStudent() {
        return new Student(0, Fakeit.name().name(), Fakeit.address().streetAddress(),
                BASE_URL + random.nextInt(1084), 0);
    }

    public void updateStudentsSettingOrder(List<Student> students) {
        for (int i = 0; i < students.size(); i++) {
            students.get(i).setOrder(i+1);
            updateStudent(students.get(i));
            updateStudentsLiveData();
        }
    }

    private void updateStudent(Student newStudent) {
        int index = students.indexOf(newStudent);
        if (index >= 0) {
            students.set(index, newStudent);
        }
    }

}
