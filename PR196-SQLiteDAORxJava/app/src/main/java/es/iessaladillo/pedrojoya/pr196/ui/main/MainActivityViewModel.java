package es.iessaladillo.pedrojoya.pr196.ui.main;

import android.arch.lifecycle.ViewModel;

import java.util.List;

import es.iessaladillo.pedrojoya.pr196.data.model.Student;

class MainActivityViewModel extends ViewModel {

    private List<Student> students;

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

}
