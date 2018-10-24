package pedrojoya.iessaladillo.es.pr106.ui.main;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import pedrojoya.iessaladillo.es.pr106.data.Repository;
import pedrojoya.iessaladillo.es.pr106.data.local.model.Student;

class MainActivityViewModel extends ViewModel {

    @NonNull
    private final Repository repository;
    private List<Student> students;

    MainActivityViewModel(@NonNull Repository repository) {
        this.repository = repository;
    }

    @NonNull
    List<Student> getStudents(boolean forceLoad) {
        if (students == null || forceLoad) {
            students = repository.queryStudents();
        }
        return students;
    }

    void insertStudent(@NonNull Student student) {
        repository.insertStudent(student);
    }

    void deleteStudent(@NonNull Student student) {
        repository.deleteStudent(student);
    }
}
