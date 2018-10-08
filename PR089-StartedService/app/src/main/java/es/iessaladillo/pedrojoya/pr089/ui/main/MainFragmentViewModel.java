package es.iessaladillo.pedrojoya.pr089.ui.main;

import android.app.Application;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import es.iessaladillo.pedrojoya.pr089.R;
import es.iessaladillo.pedrojoya.pr089.services.ExportedToTextFileLiveData;

@SuppressWarnings("WeakerAccess")
public class MainFragmentViewModel extends AndroidViewModel {

    @SuppressWarnings("CanBeFinal")
    private ArrayList<String> students;
    private final ExportedToTextFileLiveData exportedLiveData;

    public MainFragmentViewModel(@NonNull Application application) {
        super(application);
        students = new ArrayList<>(Arrays.asList(application.getResources().getStringArray(R.array.alumnos)));
        exportedLiveData = new ExportedToTextFileLiveData(application.getApplicationContext());
    }

    public ArrayList<String> getStudents() {
        return students;
    }

    public void deleteStudent(int position) {
        students.remove(position);
    }

    public ExportedToTextFileLiveData getExportedLiveData() {
        return exportedLiveData;
    }
    
}
