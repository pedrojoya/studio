package es.iessaladillo.pedrojoya.pr014.ui.main;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr014.data.Repository;
import es.iessaladillo.pedrojoya.pr014.data.local.model.Student;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {

    private List<Student> students;
    @NonNull
    private final Repository repository;

    public MainActivityViewModel(@NonNull Repository repository) {
        this.repository = repository;
    }

    @NonNull
    public List<Student> getStudents(boolean forceLoad) {
        if (students == null || forceLoad) {
            students = repository.queryStudents();
        }
        return students;
    }

    public void deleteStudent(@NonNull Student student) {
        repository.deleteStudent(student);
    }

}
