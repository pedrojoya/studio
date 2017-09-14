package es.iessaladillo.pedrojoya.pr212.ui.main;

import android.arch.lifecycle.ViewModel;

import java.util.List;

import es.iessaladillo.pedrojoya.pr212.data.Repository;
import es.iessaladillo.pedrojoya.pr212.data.model.Student;

class MainActivityViewModel extends ViewModel {

    private final Repository repository;
    private List<Student> students;

    public MainActivityViewModel(Repository repository) {
        this.repository = repository;

    }

    public List<Student> getStudents(boolean forceLoad) {
        if (forceLoad) {
            students = repository.getStudents();
        }
        return students;
    }

}
