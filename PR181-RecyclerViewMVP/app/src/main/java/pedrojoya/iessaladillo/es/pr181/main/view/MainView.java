package pedrojoya.iessaladillo.es.pr181.main.view;

import java.util.List;

import pedrojoya.iessaladillo.es.pr181.main.model.entity.Student;

public interface MainView {
    void showStudentList(List<Student> students);
    void notifyStudentAdded(Student student);
    void notifyStudentRemoved(int position, Student student);
    void showLoading();
    void hideLoading();
}
