package pedrojoya.iessaladillo.es.pr227.ui.main;

import android.arch.lifecycle.ViewModel;

import java.util.List;

import pedrojoya.iessaladillo.es.pr227.data.local.Repository;
import pedrojoya.iessaladillo.es.pr227.data.model.Student;

class MainActivityViewModel extends ViewModel {

    private final Repository repository;
    private List<Student> students;

    public MainActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    public List<Student> getStudents() {
        if (students == null) {
            students = repository.getStudents();
        }
        return students;
    }

    public void addFakeStudent() {
        repository.addFakeStudent();
    }

    public void deleteStudent(int position) {
        repository.deleteStudent(position);
    }
}
