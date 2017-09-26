package es.iessaladillo.pedrojoya.pr194.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import es.iessaladillo.pedrojoya.pr194.data.remote.StudentsLiveData;
import es.iessaladillo.pedrojoya.pr194.data.remote.StudentsRequest;

class MainActivityViewModel extends AndroidViewModel {

    private final StudentsLiveData studentsLiveData;

    public MainActivityViewModel(Application application) {
        super(application);
        studentsLiveData = new StudentsLiveData(application.getApplicationContext());
    }

    public LiveData<StudentsRequest> getStudents() {
        return studentsLiveData;
    }

    public void forceLoadStudents() {
        studentsLiveData.loadData();
    }

}
