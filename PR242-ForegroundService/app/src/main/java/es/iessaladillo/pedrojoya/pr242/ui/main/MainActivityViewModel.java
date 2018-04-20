package es.iessaladillo.pedrojoya.pr242.ui.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;

import es.iessaladillo.pedrojoya.pr242.R;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends AndroidViewModel {

    @SuppressWarnings("CanBeFinal")
    private ArrayList<String> students;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        students = new ArrayList<>(Arrays.asList(application.getResources().getStringArray(R.array.alumnos)));
    }

    public ArrayList<String> getStudents() {
        return students;
    }

    public void deleteStudent(int position) {
        students.remove(position);
    }

}
