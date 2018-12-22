package pedrojoya.iessaladillo.es.pr230.ui.main;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import pedrojoya.iessaladillo.es.pr230.data.Repository;
import pedrojoya.iessaladillo.es.pr230.data.local.model.Student;

class MainActivityViewModel extends ViewModel {

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

}
