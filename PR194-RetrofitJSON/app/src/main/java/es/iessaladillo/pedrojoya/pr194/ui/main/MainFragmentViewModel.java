package es.iessaladillo.pedrojoya.pr194.ui.main;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr194.base.Event;
import es.iessaladillo.pedrojoya.pr194.base.Resource;
import es.iessaladillo.pedrojoya.pr194.data.Repository;
import es.iessaladillo.pedrojoya.pr194.data.model.Student;

class MainFragmentViewModel extends ViewModel {

    private static final String STUDENTS_TAG = MainFragmentViewModel.class.getSimpleName();

    private final MutableLiveData<Boolean> queryTrigger = new MutableLiveData<>();
    private final LiveData<Boolean> loading;
    private final MediatorLiveData<Event<String>> message = new MediatorLiveData<>();
    private final MediatorLiveData<List<Student>> students = new MediatorLiveData<>();
    private final Repository repository;

    MainFragmentViewModel(Repository repository) {
        this.repository = repository;
        LiveData<Resource<List<Student>>> queryLiveData = Transformations.switchMap(queryTrigger,
            query -> {
                repository.cancel(STUDENTS_TAG);
                return repository.queryStudents(STUDENTS_TAG);
            });
        loading = Transformations.map(queryLiveData, Resource::isLoading);
        message.addSource(queryLiveData, resource -> {
            if (resource.hasError()) {
                message.setValue(new Event<>(resource.getException().getMessage()));
            }
        });
        students.addSource(queryLiveData, resource -> {
            if (resource.hasSuccess()) {
                students.setValue(resource.getData());
            }
        });
        refreshStudents();
    }

    LiveData<Boolean> getLoading() {
        return loading;
    }

    LiveData<Event<String>> getMessage() {
        return message;
    }

    LiveData<List<Student>> getStudents() {
        return students;
    }

    void refreshStudents() {
        queryTrigger.setValue(true);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.cancel(STUDENTS_TAG);
    }

}
