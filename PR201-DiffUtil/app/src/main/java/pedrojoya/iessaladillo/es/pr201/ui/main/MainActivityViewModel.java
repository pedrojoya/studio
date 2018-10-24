package pedrojoya.iessaladillo.es.pr201.ui.main;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import pedrojoya.iessaladillo.es.pr201.data.Repository;
import pedrojoya.iessaladillo.es.pr201.data.local.model.Student;

public class MainActivityViewModel extends ViewModel implements Repository {

    @NonNull
    private final Repository repository;
    private int order = 1;
    @NonNull
    private final LiveData<List<Student>> students;

    MainActivityViewModel(@NonNull Repository respository) {
        this.repository = respository;
        students = repository.queryStudents();
    }

    public int getOrder() {
        return order;
    }

    void toggleOrder() {
        order = -order;
    }

    @Override
    @NonNull
    public LiveData<List<Student>> queryStudents() {
        return students;
    }

    @Override
    public void insertStudent(@NonNull Student student) {
        repository.insertStudent(student);
    }

    @Override
    public void deleteStudent(@NonNull Student student) {
        repository.deleteStudent(student);
    }

    @Override
    public void updateStudent(@NonNull Student student, @NonNull Student newStudent) {
        repository.updateStudent(student, newStudent);
    }

}
