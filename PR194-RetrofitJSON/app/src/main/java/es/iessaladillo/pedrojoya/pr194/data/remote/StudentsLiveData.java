package es.iessaladillo.pedrojoya.pr194.data.remote;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import es.iessaladillo.pedrojoya.pr194.data.model.Student;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentsLiveData extends LiveData<StudentsRequest> {

    private final StudentsApi api;

    public StudentsLiveData(Context context) {
        api = StudentsService.getInstance(context).getApi();
        loadData();
    }

    public void loadData() {
        Call<List<Student>> call = api.getStudents();
        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(@NonNull Call<List<Student>> call, @NonNull Response<List<Student>> response) {
                if (response.body() != null && response.isSuccessful()) {
                    setValue(StudentsRequest.newListInstance(response.body()));
                } else {
                    setValue(StudentsRequest.newErrorInstance(new Throwable(response.message())));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Student>> call, @NonNull Throwable t) {
                setValue(StudentsRequest.newErrorInstance(t));
            }
        });
    }

}
