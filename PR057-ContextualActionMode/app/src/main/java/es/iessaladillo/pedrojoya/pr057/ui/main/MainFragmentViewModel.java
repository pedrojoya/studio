package es.iessaladillo.pedrojoya.pr057.ui.main;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr057.data.Repository;
import es.iessaladillo.pedrojoya.pr057.data.local.model.Student;

class MainFragmentViewModel extends ViewModel {

    private LiveData<List<Student>> students;
    private final Repository repository;

    MainFragmentViewModel(Repository repository) {
        this.repository = repository;
    }

    LiveData<List<Student>> getStudents() {
        if (students == null) {
            students = repository.queryStudents();
        }
        return students;
    }

    void addStudent(Student student) {
        repository.insertStudent(student);
    }

    void deleteStudent(Student student) {
        repository.deleteStudent(student);
    }

}
