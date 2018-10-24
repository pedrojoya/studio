package es.iessaladillo.pedrojoya.pr011.ui.main;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr011.data.Repository;

class MainActivityViewModel extends ViewModel {

    private List<String> students;
    @NonNull
    private final Repository repository;

    MainActivityViewModel(@NonNull Repository repository) {
        this.repository = repository;
    }

    List<String> getStudents() {
        if (students == null) {
            students = repository.queryStudents();
        }
        return students;
    }

    void addStudent(@NonNull String student) {
        repository.addStudent(student);
    }

    void deleteStudent(@NonNull String student) {
        repository.deleteStudent(student);
    }

}
