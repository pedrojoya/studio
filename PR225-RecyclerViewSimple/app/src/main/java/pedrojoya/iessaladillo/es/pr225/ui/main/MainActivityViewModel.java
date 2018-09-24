package pedrojoya.iessaladillo.es.pr225.ui.main;

import androidx.lifecycle.ViewModel;

import java.util.List;

import pedrojoya.iessaladillo.es.pr225.data.Repository;
import pedrojoya.iessaladillo.es.pr225.data.local.model.Student;

class MainActivityViewModel extends ViewModel {

    private final Repository repository;
    private List<Student> students;

    public MainActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    public List<Student> getStudents() {
        if (students == null) {
            students = repository.queryStudents();
        }
        return students;
    }

}
