package pedrojoya.iessaladillo.es.pr232.ui.main;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pedrojoya.iessaladillo.es.pr232.data.local.Database;
import pedrojoya.iessaladillo.es.pr232.data.model.Student;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {

    private final Database database;
    private int order = 1;
    private ArrayList<Student> students;
    private String searchQuery;
    private boolean searchViewExpanded;

    public MainActivityViewModel() {
        database = Database.getInstance();
    }

    public List<Student> toggleOrder() {
        order = -order;
        return getStudents(true);
    }

    public List<Student> getStudents(boolean forceLoad) {
        if (students == null || forceLoad) {
            ArrayList<Student> orderedStudents = new ArrayList<>();
            for (Student s : database.getStudents()) {
                orderedStudents.add(new Student(s));
            }
            Collections.sort(orderedStudents, (student1, student2) -> order * student1.getName().compareTo(student2.getName()));
            students = orderedStudents;
        }
        return students;
    }

    public void removeStudent(Student student) {
        database.removeStudent(student);
    }

    public void insertStudent() {
        database.addStudent(database.createStudent());
    }

    public void updateStudent(Student student, Student newStudent) {
        database.updateStudent(student, newStudent);
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public boolean isSearchViewExpanded() {
        return searchViewExpanded;
    }

    public void setSearchViewExpanded(boolean searchViewExpanded) {
        this.searchViewExpanded = searchViewExpanded;
    }

}
