package es.iessaladillo.pedrojoya.pr249.ui.main;

import java.util.List;

import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr249.data.Repository;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {

    private final Repository repository;
    private List<String> students;

    MainActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    @SuppressWarnings("SameParameterValue")
    public List<String> getStudents(boolean forceLoad) {
        if (students == null || forceLoad) {
            students = repository.queryStudents();
        }
        return students;
    }

}
