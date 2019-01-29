package es.iessaladillo.pedrojoya.pr089.ui.main;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr089.base.Event;
import es.iessaladillo.pedrojoya.pr089.base.Resource;
import es.iessaladillo.pedrojoya.pr089.services.ExportToTextFileService;

class MainFragmentViewModel extends ViewModel {

    private final List<String> _students;
    private final MutableLiveData<List<String>> students = new MutableLiveData<>();
    private final MediatorLiveData<Event<Uri>> uri = new MediatorLiveData<>();
    private final MediatorLiveData<Event<String>> errorMessage = new MediatorLiveData<>();
    private final LiveData<Boolean> loading;

    MainFragmentViewModel(List<String> students) {
        this._students = students;
        updateStudentsLiveData();
        uri.addSource(ExportToTextFileService.result, uriResource -> {
            if (uriResource.hasSuccess()) {
                uri.setValue(new Event<>(uriResource.getData()));
            }
        });
        errorMessage.addSource(ExportToTextFileService.result, uriResource -> {
            if (uriResource.hasError()) {
                errorMessage.setValue(new Event<>(uriResource.getException().getMessage()));
            }
        });
        loading = Transformations.map(ExportToTextFileService.result, Resource::isLoading);
    }

    LiveData<List<String>> getStudents() {
        return students;
    }

    void deleteStudent(int position) {
        _students.remove(position);
        updateStudentsLiveData();
    }

    private void updateStudentsLiveData() {
        students.setValue(new ArrayList<>(_students));
    }

    LiveData<Event<Uri>> getUri() {
        return uri;
    }

    LiveData<Event<String>> getErrorMessage() {
        return errorMessage;
    }

    LiveData<Boolean> getLoading() { return loading; }
}
