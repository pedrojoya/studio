package es.iessaladillo.pedrojoya.pr169.data.remote;

import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import es.iessaladillo.pedrojoya.pr169.BuildConfig;
import es.iessaladillo.pedrojoya.pr169.base.Event;
import es.iessaladillo.pedrojoya.pr169.base.RequestState;
import es.iessaladillo.pedrojoya.pr169.data.models.TranslateResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YandexLiveData extends MutableLiveData<RequestState> {

    private final Api api;
    private Call<TranslateResponse> call;

    public YandexLiveData(Api api) {
        this.api = api;
    }

    public void translate(String word) {
        postValue(new RequestState.Loading(true));
        call = api.getTranslation(BuildConfig.YANDEX_API_KEY, word, Constants.LANG);
        call.enqueue(new Callback<TranslateResponse>() {
            @Override
            public void onResponse(@NonNull Call<TranslateResponse> call,
                    @NonNull Response<TranslateResponse> response) {
                if (response.body() != null && response.isSuccessful()) {
                    postValue(new RequestState.Result<>(response.body()));
                } else {
                    postValue(new RequestState.Error(new Event<>(new Exception(response.message()))));
                }
            }

            @Override
            public void onFailure(@NonNull Call<TranslateResponse> call, @NonNull Throwable t) {
                postValue(new RequestState.Error(new Event<>(new Exception(t.getMessage()))));
            }
        });
    }

    public void cancel() {
        if (call != null) {
            call.cancel();
        }
    }

}
