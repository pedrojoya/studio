package pedrojoya.iessaladillo.es.pr247.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import java.util.List;

import pedrojoya.iessaladillo.es.pr247.data.local.Repository;
import pedrojoya.iessaladillo.es.pr247.data.model.Student;

public class MainActivityViewModel extends ViewModel {

    private final Repository repository;
    private LiveData<PagedList<Student>> students;
    private PagedList.Config config = new PagedList.Config.Builder()
            .setPageSize(15)
            .setEnablePlaceholders(true)
            .build();

    public MainActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    // El viewmodel debería construir el LiveData<PagedList<Student>> a partir de la factoría
    // proporcionada por éste método, mediante LivePagedListBuilder(factory, tamaño_página).build()
    public LiveData<PagedList<Student>> queryPagedStudents() {
        if (students == null) {
            students = (new LivePagedListBuilder<Integer, Student>(repository.queryPagedStudents(), config).build());
        }
        return students;
    }

    public void addFakeStudent() {
        repository.addFakeStudent();
    }

    public void deleteStudent(int position) {
        repository.deleteStudent(position);
    }
}
