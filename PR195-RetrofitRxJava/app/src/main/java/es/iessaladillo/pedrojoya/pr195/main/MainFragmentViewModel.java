package es.iessaladillo.pedrojoya.pr195.main;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr195.base.Event;
import es.iessaladillo.pedrojoya.pr195.data.Repository;
import es.iessaladillo.pedrojoya.pr195.data.model.Student;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

class MainFragmentViewModel extends ViewModel {

    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<Event<String>> message = new MutableLiveData<>();
    private final MutableLiveData<List<Student>> students = new MutableLiveData<>();
    private final Repository repository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    MainFragmentViewModel(Repository repository) {
        this.repository = repository;
        refreshStudents();
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public LiveData<Event<String>> getMessage() {
        return message;
    }

    LiveData<List<Student>> getStudents() {
        return students;
    }

    void refreshStudents() {
        loading.setValue(true);
        compositeDisposable.add(repository.queryStudents()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(result -> {
                    students.setValue(result);
                    loading.setValue(false);
                },
                throwable -> {
                    message.setValue(new Event<>(throwable.getMessage()));
                    loading.setValue(false);
                }));
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }

}
