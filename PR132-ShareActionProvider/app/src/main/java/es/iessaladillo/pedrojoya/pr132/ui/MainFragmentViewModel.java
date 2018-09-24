package es.iessaladillo.pedrojoya.pr132.ui;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr132.data.Repository;

@SuppressWarnings("WeakerAccess")
public class MainFragmentViewModel extends ViewModel {

    private List<String> students;
    private final Repository repository;

    public MainFragmentViewModel(Repository repository) {
        this.repository = repository;
    }

    public List<String> getStudents() {
        if (students == null) {
            students = repository.queryStudents();
        }
        return students;
    }

    void deleteStudent(String student) {
        ArrayList<String> newList = new ArrayList<>(students);
        newList.remove(student);
        students = newList;
    }

}
