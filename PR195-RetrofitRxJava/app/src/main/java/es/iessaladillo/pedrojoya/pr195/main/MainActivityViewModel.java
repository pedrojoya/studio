package es.iessaladillo.pedrojoya.pr195.main;

import android.arch.lifecycle.ViewModel;

import java.util.List;

import es.iessaladillo.pedrojoya.pr195.data.Repository;
import es.iessaladillo.pedrojoya.pr195.data.model.Student;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

class MainActivityViewModel extends ViewModel {

    private final Repository repository;
    private Observable<List<Student>> studentsObservable;

    public MainActivityViewModel(Repository repository) {
        this.repository = repository;
        studentsObservable = loadStudents();
    }

    public Observable<List<Student>> getStudents(boolean forceLoad) {
        if (forceLoad) {
            studentsObservable = loadStudents();
        }
        return studentsObservable;
    }

    private Observable<List<Student>> loadStudents() {
        return repository.getStudents().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).cache();
    }

}
