package es.iessaladillo.pedrojoya.pr057.ui.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.lifecycle.ViewModel;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {

    private List<String> subjects;

    public List<String> getSubjects() {
        if (subjects == null) {
           subjects = new ArrayList<>(Arrays.asList("Android", "Acceso a datos", "Libre configuración"));
        }
        return subjects;
    }

}
