package pedrojoya.iessaladillo.es.pr229.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import pedrojoya.iessaladillo.es.pr229.data.Repository;
import pedrojoya.iessaladillo.es.pr229.data.local.model.Student;

public class MainActivityViewModel extends ViewModel implements Repository {

    private final Repository repository;
    private int order = 1;
    private final LiveData<List<Student>> students;

    MainActivityViewModel(Repository respository) {
        this.repository = respository;
        students = repository.queryStudents();
    }

    int getOrder() {
        return order;
    }

    void toggleOrder() {
        order = -order;
    }

    @Override
    public LiveData<List<Student>> queryStudents() {
        return students;
    }

    @Override
    public void insertStudent(Student student) {
        repository.insertStudent(student);
    }

    @Override
    public void deleteStudent(Student student) {
        repository.deleteStudent(student);
    }

    @Override
    public void updateStudent(Student student, Student newStudent) {
        repository.updateStudent(student, newStudent);
    }

}
