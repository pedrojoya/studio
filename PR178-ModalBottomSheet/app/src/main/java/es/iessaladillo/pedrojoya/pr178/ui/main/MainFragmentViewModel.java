package es.iessaladillo.pedrojoya.pr178.ui.main;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr178.data.Repository;
import es.iessaladillo.pedrojoya.pr178.data.local.model.Student;

class MainFragmentViewModel extends ViewModel {

    private final LiveData<List<Student>> students;
    private final Repository repository;

    MainFragmentViewModel(Repository repository) {
        this.repository = repository;
        students = this.repository.getStudents();
    }

    LiveData<List<Student>> getStudents() {
        return students;
    }

    void deleteStudent(Student student) {
        repository.deleteStudent(student);
    }

    void addStudent(Student student) {
        repository.insertStudent(student);
    }

}
