package es.iessaladillo.pedrojoya.pr057.data.local;

import com.mooveit.library.Fakeit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr057.data.local.model.Student;

public class Database {

    private static final String BASE_URL = "https://picsum.photos/200/300?image=";
    @NonNull
    private static final Random random = new Random();
    private static volatile Database instance;
    @NonNull
    private final ArrayList<Student> students = new ArrayList<>();
    @NonNull
    private final MutableLiveData<List<Student>> studentsLiveData = new MutableLiveData<>();
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

    @NonNull
    public static Student newFakeStudent() {
        return new Student(0, Fakeit.name().name(), Fakeit.address().streetAddress(),
            Fakeit.phone().formats(), Fakeit.gameOfThrones().house(), BASE_URL + random.nextInt(1084),
            random.nextInt(10) + 16, random.nextBoolean());
    }

    private void insertInitialData() {
        for (int i = 0; i < 10; i++) {
            insertStudent(newFakeStudent());
        }
    }

    @NonNull
    public LiveData<List<Student>> queryStudents() {
        return studentsLiveData;
    }

    public synchronized void insertStudent(@NonNull Student student) {
        student.setId(++studentsAutoId);
        students.add(student);
        updateStudentsLiveData();
    }

    public synchronized void deleteStudent(@NonNull Student student) {
        students.remove(student);
        updateStudentsLiveData();
    }

    private void updateStudentsLiveData() {
        ArrayList<Student> sortedList = new ArrayList<>(students);
        Collections.sort(sortedList,
            (student1, student2) -> student1.getName().compareTo(student2.getName()));
        studentsLiveData.postValue(sortedList);
    }

}
