package es.iessaladillo.pedrojoya.pr040.ui.main;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import es.iessaladillo.pedrojoya.pr040.base.RequestState;
import es.iessaladillo.pedrojoya.pr040.data.remote.StudentsLiveData;

@SuppressLint("StaticFieldLeak")
class MainActivityViewModel extends ViewModel {

    private final StudentsLiveData studentsLiveData;

    public MainActivityViewModel() {
        studentsLiveData = new StudentsLiveData();
    }

    public LiveData<RequestState> getStudents() {
        return studentsLiveData;
    }

    public void forceLoadStudents() {
        studentsLiveData.loadData();
    }

    @Override
    protected void onCleared() {
        studentsLiveData.cancel();
        super.onCleared();
    }

}
