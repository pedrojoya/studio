package es.iessaladillo.pedrojoya.pr083.ui.main;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.android.volley.RequestQueue;

import es.iessaladillo.pedrojoya.pr083.base.RequestState;
import es.iessaladillo.pedrojoya.pr083.data.remote.StudentsLiveData;

@SuppressLint("StaticFieldLeak")
class MainActivityViewModel extends ViewModel {

    private final StudentsLiveData studentsLiveData;

    public MainActivityViewModel(RequestQueue requestQueue, String urlString) {
        studentsLiveData = new StudentsLiveData(requestQueue, urlString);
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
