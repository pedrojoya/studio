package es.iessaladillo.pedrojoya.pr040.ui.main;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr040.base.Event;
import es.iessaladillo.pedrojoya.pr040.data.Repository;
import es.iessaladillo.pedrojoya.pr040.data.model.Student;

class MainFragmentViewModel extends ViewModel {

    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<Event<String>> message = new MutableLiveData<>();
    private final MutableLiveData<List<Student>> students = new MutableLiveData<>();
    private final Repository repository;

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
        repository.queryStudents(new Repository.Callback<List<Student>>() {
            @Override
            public void onFailure(Exception exception) {
                message.setValue(new Event<>(exception.getMessage()));
                loading.setValue(false);
            }

            @Override
            public void onResponse(List<Student> result) {
                students.setValue(result);
                loading.setValue(false);
            }
        });
    }

}
