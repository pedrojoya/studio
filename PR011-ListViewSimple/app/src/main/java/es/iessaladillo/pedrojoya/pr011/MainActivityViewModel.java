package es.iessaladillo.pedrojoya.pr011;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr011.data.Repository;

class MainActivityViewModel extends ViewModel {

    private ArrayList<String> data;
    private final Repository repository;

    public MainActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    public ArrayList<String> getData() {
        if (data == null) {
            data = (ArrayList<String>) repository.getStudents();
        }
        return data;
    }

}
