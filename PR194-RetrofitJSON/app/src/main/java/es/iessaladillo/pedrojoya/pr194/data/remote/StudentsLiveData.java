package es.iessaladillo.pedrojoya.pr194.data.remote;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import es.iessaladillo.pedrojoya.pr194.base.Event;
import es.iessaladillo.pedrojoya.pr194.base.RequestState;
import es.iessaladillo.pedrojoya.pr194.data.model.Student;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentsLiveData extends MutableLiveData<RequestState> {

    private final Api api;
    private Call<List<Student>> call;

    public StudentsLiveData(Api api) {
        this.api = api;
        loadData();
    }

    public void loadData() {
        postValue(new RequestState.Loading(true));
        call = api.getStudents();
        call.enqueue(new Callback<List<Student>>() {
            @SuppressWarnings("unchecked")
            @Override
            public void onResponse(@NonNull Call<List<Student>> call, @NonNull Response<List<Student>> response) {
                if (response.body() != null && response.isSuccessful()) {
                    postValue(new RequestState.Result<>(response.body()));
                } else {
                    postValue(new RequestState.Error(new Event(new Exception(response.message()))));
                }
            }

            @SuppressWarnings("unchecked")
            @Override
            public void onFailure(@NonNull Call<List<Student>> call, @NonNull Throwable t) {
                postValue(new RequestState.Error(new Event(new Exception(t.getMessage()))));
            }
        });
    }

    public void cancel() {
        if (call != null) {
            call.cancel();
        }
    }

}
