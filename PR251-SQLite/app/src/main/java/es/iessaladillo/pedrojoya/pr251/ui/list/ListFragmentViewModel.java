package es.iessaladillo.pedrojoya.pr251.ui.list;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr251.R;
import es.iessaladillo.pedrojoya.pr251.base.Event;
import es.iessaladillo.pedrojoya.pr251.base.Resource;
import es.iessaladillo.pedrojoya.pr251.data.Repository;
import es.iessaladillo.pedrojoya.pr251.data.local.model.Student;

class ListFragmentViewModel extends ViewModel {

    private final Application application;
    private final LiveData<List<Student>> students;
    private final MutableLiveData<Student> deleteTrigger = new MutableLiveData<>();
    private final LiveData<Resource<Integer>> deletionResult;
    private final MediatorLiveData<Event<String>> successMessage = new MediatorLiveData<>();
    private final MediatorLiveData<Event<String>> errorMessage = new MediatorLiveData<>();

    ListFragmentViewModel(Application application, Repository repository) {
        this.application = application;
        students = repository.queryStudents();
        deletionResult = Transformations.switchMap(deleteTrigger, repository::deleteStudent);
        setupSuccessMessage();
        setupErrorMessage();
    }

    private void setupSuccessMessage() {
        successMessage.addSource(deletionResult, resource -> {
            if (resource.hasSuccess()) {
                successMessage.setValue(new Event<>(application.getString(R.string.list_deleted_successfully)));
            }
        });
    }

    private void setupErrorMessage() {
        errorMessage.addSource(deletionResult, resource -> {
            if (resource.hasError()) {
                errorMessage.setValue(new Event<>(application.getString(R.string.list_error_deleting)));
            }
        });
    }

    LiveData<List<Student>> getStudents() {
        return students;
    }

    LiveData<Event<String>> getSuccessMessage() {
        return successMessage;
    }

    LiveData<Event<String>> getErrorMessage() {
        return errorMessage;
    }

    void deleteStudent(Student student) {
        deleteTrigger.setValue(student);
    }

}
