package es.iessaladillo.pedrojoya.pr011.ui.main;

import androidx.lifecycle.ViewModel;

import java.util.List;

import es.iessaladillo.pedrojoya.pr011.data.Repository;

class MainActivityViewModel extends ViewModel {

    private List<String> students;
    private final Repository repository;

    MainActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    List<String> getStudents() {
        if (students == null) {
            students = repository.queryStudents();
        }
        return students;
    }

    void addStudent(String student) {
        repository.addStudent(student);
    }

    void deleteStudent(String student) {
        repository.deleteStudent(student);
    }

}
