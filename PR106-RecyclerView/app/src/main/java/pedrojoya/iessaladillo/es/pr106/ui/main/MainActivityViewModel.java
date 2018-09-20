package pedrojoya.iessaladillo.es.pr106.ui.main;

import android.arch.lifecycle.ViewModel;

import java.util.List;

import pedrojoya.iessaladillo.es.pr106.data.Repository;
import pedrojoya.iessaladillo.es.pr106.data.local.model.Student;

class MainActivityViewModel extends ViewModel {

    private final Repository repository;
    private List<Student> students;

    MainActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    List<Student> getStudents(boolean forceLoad) {
        if (students == null || forceLoad) {
            students = repository.queryStudents();
        }
        return students;
    }

    void insertStudent(Student student) {
        repository.insertStudent(student);
    }

    void deleteStudent(Student student) {
        repository.deleteStudent(student);
    }
}
