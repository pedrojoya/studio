package pedrojoya.iessaladillo.es.pr201.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import pedrojoya.iessaladillo.es.pr201.data.Repository;
import pedrojoya.iessaladillo.es.pr201.data.local.model.Student;

public class MainActivityViewModel extends ViewModel implements Repository {

    private final Repository repository;
    private int order = 1;
    private final LiveData<List<Student>> students;

    public MainActivityViewModel(Repository respository) {
        this.repository = respository;
        students = repository.queryStudents();
    }

    public int getOrder() {
        return order;
    }

    public void toggleOrder() {
        order = -order;
    }

    @Override
    public LiveData<List<Student>> queryStudents() {
        return students;
    }

    @Override
    public void addStudent(Student student) {
        repository.addStudent(student);
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
