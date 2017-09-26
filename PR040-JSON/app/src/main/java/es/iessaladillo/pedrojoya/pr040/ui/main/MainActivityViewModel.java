package es.iessaladillo.pedrojoya.pr040.ui.main;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import es.iessaladillo.pedrojoya.pr040.data.remote.StudentsLiveData;
import es.iessaladillo.pedrojoya.pr040.data.remote.StudentsRequest;

@SuppressLint("StaticFieldLeak")
class MainActivityViewModel extends AndroidViewModel {

    private final StudentsLiveData studentsLiveData;

    public MainActivityViewModel(Application application) {
        super(application);
        studentsLiveData = new StudentsLiveData();
    }

    public LiveData<StudentsRequest> getStudents() {
        return studentsLiveData;
    }

    public void forceLoadStudents() {
        studentsLiveData.loadData();
    }
}
