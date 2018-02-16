package es.iessaladillo.pedrojoya.pr218.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.util.Log;

import java.util.List;

import es.iessaladillo.pedrojoya.pr218.data.Repository;
import es.iessaladillo.pedrojoya.pr218.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr218.data.model.Student;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

class MainActivityViewModel extends AndroidViewModel {

    private final Repository repository;
    private LiveData<List<Student>> studentsLiveData;

    public MainActivityViewModel(Application application) {
        super(application);
        repository = RepositoryImpl.getInstance(application.getApplicationContext());
        getStudents(true);
    }

    public LiveData<List<Student>> getStudents(boolean forceLoad) {
        if (studentsLiveData == null || forceLoad) {
            loadStudents();
        }
        return studentsLiveData;
    }

    private void loadStudents() {
        Log.d(getClass().getName(), "Fetching data");
        Flowable<List<Student>> studentsObservable = repository.getStudents()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .toFlowable(BackpressureStrategy.LATEST).cache();
        studentsLiveData = LiveDataReactiveStreams.fromPublisher(studentsObservable);
    }

}
