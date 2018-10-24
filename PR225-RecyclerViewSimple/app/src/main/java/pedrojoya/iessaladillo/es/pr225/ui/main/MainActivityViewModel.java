package pedrojoya.iessaladillo.es.pr225.ui.main;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import pedrojoya.iessaladillo.es.pr225.data.Repository;
import pedrojoya.iessaladillo.es.pr225.data.local.model.Student;

class MainActivityViewModel extends ViewModel {

    @NonNull
    private final Repository repository;
    private List<Student> students;

    MainActivityViewModel(@NonNull Repository repository) {
        this.repository = repository;
    }

    @NonNull
    List<Student> getStudents() {
        if (students == null) {
            students = repository.queryStudents();
        }
        return students;
    }

}
