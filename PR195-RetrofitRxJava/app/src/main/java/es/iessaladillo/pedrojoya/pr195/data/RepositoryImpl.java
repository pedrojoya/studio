package es.iessaladillo.pedrojoya.pr195.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr195.base.Event;
import es.iessaladillo.pedrojoya.pr195.base.RequestState;
import es.iessaladillo.pedrojoya.pr195.data.model.Student;
import es.iessaladillo.pedrojoya.pr195.data.remote.Api;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RepositoryImpl implements Repository {

    private static RepositoryImpl instance;

    private final Api api;
    private final MutableLiveData<RequestState<List<Student>>> studentsLiveData = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private RepositoryImpl(Api api) {
        this.api = api;
    }

    public static RepositoryImpl getInstance(Api api) {
        if (instance == null) {
            synchronized (RepositoryImpl.class) {
                if (instance == null) {
                    instance = new RepositoryImpl(api);
                }
            }
        }
        return instance;
    }

    @Override
    public LiveData<RequestState<List<Student>>> getStudents() {
        studentsLiveData.setValue(new RequestState.Loading<>(true));
        compositeDisposable.add(api.getStudents()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::showStudents, this::showErrorLoadingStudents));
        return studentsLiveData;
    }

    private void showErrorLoadingStudents(Throwable throwable) {
        studentsLiveData.setValue(new RequestState.Error<>(new Event<>(new Exception(throwable.getMessage()))));
    }

    private void showStudents(List<Student> students) {
        studentsLiveData.setValue(new RequestState.Result<>(students));
    }

    @Override
    public void cancel() {
        compositeDisposable.clear();
    }

}
