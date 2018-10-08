package es.iessaladillo.pedrojoya.pr140.data.remote;

import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import es.iessaladillo.pedrojoya.pr140.base.Event;
import es.iessaladillo.pedrojoya.pr140.base.RequestState;
import es.iessaladillo.pedrojoya.pr140.data.remote.model.Counting;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountingLiveData extends MutableLiveData<RequestState> {

    private final Api api;
    private Call<Counting> call;

    public CountingLiveData(Api api) {
        this.api = api;
    }

    public void loadData(String cityCode) {
        postValue(new RequestState.Loading(true));
        call = api.getCityCounting(cityCode);
        call.enqueue(new Callback<Counting>() {
            @SuppressWarnings("unchecked")
            @Override
            public void onResponse(@NonNull Call<Counting> call, @NonNull Response<Counting> response) {
                if (response.body() != null && response.isSuccessful()) {
                    postValue(new RequestState.Result<>(response.body()));
                } else {
                    postValue(new RequestState.Error(new Event(new Exception(response.message()))));
                }
            }

            @SuppressWarnings("unchecked")
            @Override
            public void onFailure(@NonNull Call<Counting> call, @NonNull Throwable t) {
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
