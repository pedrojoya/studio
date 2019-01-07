package es.iessaladillo.pedrojoya.pr040.ui.main;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr040.base.Call;
import es.iessaladillo.pedrojoya.pr040.base.Resource;
import es.iessaladillo.pedrojoya.pr040.data.Repository;
import es.iessaladillo.pedrojoya.pr040.data.remote.model.Student;

class MainFragmentViewModel extends ViewModel {

    private final LiveData<Resource<List<Student>>> students;
    private final MutableLiveData<Boolean> queryStudentsTrigger = new MutableLiveData<>();
    private Call<Resource<List<Student>>> previousCall;

    MainFragmentViewModel(Repository repository) {
        students = Transformations.switchMap(queryStudentsTrigger, query -> {
            cancelPreviousCall();
            previousCall = repository.queryStudents();
            return previousCall;
        });
        refreshStudents();
    }

    private void cancelPreviousCall() {
        if (previousCall != null) {
            previousCall.cancel(true);
        }
    }

    LiveData<Resource<List<Student>>> getStudents() {
        return students;
    }

    void refreshStudents() {
        queryStudentsTrigger.postValue(true);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        cancelPreviousCall();
    }

}
