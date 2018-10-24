package pedrojoya.iessaladillo.es.pr226.ui.main;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import pedrojoya.iessaladillo.es.pr226.data.Repository;
import pedrojoya.iessaladillo.es.pr226.data.local.model.Student;

class MainActivityViewModel extends ViewModel {

    @NonNull
    private final Repository repository;
    private LiveData<List<Student>> students;

    MainActivityViewModel(@NonNull Repository repository) {
        this.repository = repository;
    }

    LiveData<List<Student>> getStudents() {
        if (students == null) {
            students = repository.getStudents();
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