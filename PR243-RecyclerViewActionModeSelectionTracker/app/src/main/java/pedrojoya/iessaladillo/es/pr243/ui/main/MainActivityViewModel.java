package pedrojoya.iessaladillo.es.pr243.ui.main;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pedrojoya.iessaladillo.es.pr243.data.Repository;
import pedrojoya.iessaladillo.es.pr243.data.local.model.Student;

class MainActivityViewModel extends ViewModel {

    private boolean inActionMode = false;
    private final Repository repository;
    private List<Student> students;

    public MainActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    public List<Student> getStudents(boolean forceLoad) {
        if (students == null || forceLoad) {
            ArrayList<Student> orderedStudents = new ArrayList<>();
            for (Student s : repository.getStudents()) {
                orderedStudents.add(new Student(s));
            }
            Collections.sort(orderedStudents, (student1, student2) -> student1.getName().compareTo(student2.getName()));
            students = orderedStudents;
        }
        return students;
    }

    public boolean isInActionMode() {
        return inActionMode;
    }

    public void setInActionMode(boolean inActionMode) {
        this.inActionMode = inActionMode;
    }

    public void addStudent() {
        repository.addStudent();
    }

    public void removeStudent(Student student) {
        repository.deleteStudent(student);
    }


}
