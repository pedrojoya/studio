package es.iessaladillo.pedrojoya.pr057.main;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class MainActivityViewModel extends ViewModel {

    private List<String> subjects;

    public List<String> getSubjects() {
        if (subjects == null) {
           subjects = new ArrayList<>(Arrays.asList("Android", "Acceso a datos", "Libre configuración"));
        }
        return subjects;
    }

}
