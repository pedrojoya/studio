package pedrojoya.iessaladillo.es.pr243.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import pedrojoya.iessaladillo.es.pr243.data.Repository;
import pedrojoya.iessaladillo.es.pr243.data.local.model.Student;

class MainActivityViewModel extends ViewModel {

    private boolean inActionMode = false;
    private final Repository repository;
    private LiveData<List<Student>> students;

    MainActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    LiveData<List<Student>> getStudents() {
        if (students == null) {
            students = repository.queryStudents();
        }
        return students;
    }

    void insertStudent(Student student) {
        repository.insertStudent(student);
    }

    @SuppressWarnings("unused")
    void deleteStudent(Student student) {
        repository.deleteStudent(student);
    }

    boolean isInActionMode() {
        return inActionMode;
    }

    void setInActionMode(boolean inActionMode) {
        this.inActionMode = inActionMode;
    }

    void deleteStudents(List<Student> students) {
       repository.deleteStudents(students);
    }
}
