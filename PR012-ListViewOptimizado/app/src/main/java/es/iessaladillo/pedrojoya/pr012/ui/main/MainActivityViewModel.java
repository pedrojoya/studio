package es.iessaladillo.pedrojoya.pr012.ui.main;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr012.data.Repository;
import es.iessaladillo.pedrojoya.pr012.data.local.model.Student;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {

    private ArrayList<Student> data;
    private final Repository repository;

    public MainActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    public ArrayList<Student> getData() {
        if (data == null) {
            data = (ArrayList<Student>) repository.getStudents();
        }
        return data;
    }

}
