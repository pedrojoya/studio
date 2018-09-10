package pedrojoya.iessaladillo.es.pr231.ui.main;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pedrojoya.iessaladillo.es.pr231.data.Repository;
import pedrojoya.iessaladillo.es.pr231.data.local.model.Student;

public class MainActivityViewModel extends ViewModel {

    private final Repository repository;
    private List<Student> students;

    public MainActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    @SuppressWarnings("SameParameterValue")
    public List<Student> getStudents() {
        if (students == null) {
            ArrayList<Student> orderedStudents = new ArrayList<>();
            for (Student s : repository.getStudents()) {
                orderedStudents.add(new Student(s));
            }
            Collections.sort(orderedStudents, (student1, student2) -> student1.getName().compareTo(student2.getName()));
            students = orderedStudents;
        }
        return students;
    }

}
