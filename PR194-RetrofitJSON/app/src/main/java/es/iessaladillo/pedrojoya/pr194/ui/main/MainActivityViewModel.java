package es.iessaladillo.pedrojoya.pr194.ui.main;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import es.iessaladillo.pedrojoya.pr194.base.RequestState;
import es.iessaladillo.pedrojoya.pr194.data.remote.Api;
import es.iessaladillo.pedrojoya.pr194.data.remote.StudentsLiveData;

@SuppressLint("StaticFieldLeak")
class MainActivityViewModel extends ViewModel {

    private final StudentsLiveData studentsLiveData;

    public MainActivityViewModel(Api api) {
        studentsLiveData = new StudentsLiveData(api);
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
