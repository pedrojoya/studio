package es.iessaladillo.pedrojoya.pr195.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.util.Log;

import java.util.List;

import es.iessaladillo.pedrojoya.pr195.data.Repository;
import es.iessaladillo.pedrojoya.pr195.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr195.data.model.Student;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

class MainActivityViewModel extends AndroidViewModel {

    private final Repository repository;
    private Observable<List<Student>> studentsObservable;

    public MainActivityViewModel(Application application) {
        super(application);
        repository = RepositoryImpl.getInstance(application.getApplicationContext());
        /* Nuevo */
        loadStudents();
        /*  */
    }

    public Observable<List<Student>> getStudents(boolean forceLoad) {
        if (studentsObservable == null || forceLoad) {
            loadStudents();
        }
        return studentsObservable;
    }

    private void loadStudents() {
        Log.d(getClass().getName(), "Fetching data");
        studentsObservable = repository.getStudents().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).cache();
    }

}
