package es.iessaladillo.pedrojoya.pr169.data.remote;

import android.support.annotation.NonNull;

import es.iessaladillo.pedrojoya.pr169.BuildConfig;
import es.iessaladillo.pedrojoya.pr169.data.models.TranslateResponse;
import es.iessaladillo.pedrojoya.pr169.util.SingleLiveEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YandexLiveData extends SingleLiveEvent<YandexRequest> {

    public YandexLiveData(YandexApi yandexApi, String word) {
        getTranslation(yandexApi, word);
    }

    private void getTranslation(YandexApi yandexApi, String word) {
        Call<TranslateResponse> call = yandexApi.getTranslation(BuildConfig.YANDEX_API_KEY, word,
                Constants.LANG);
        call.enqueue(new Callback<TranslateResponse>() {
            @Override
            public void onResponse(@NonNull Call<TranslateResponse> call,
                    @NonNull Response<TranslateResponse> response) {
                if (response.body() != null && response.isSuccessful()) {
                    setValue(YandexRequest.newResponse(response.body()));
                } else {
                    setValue(YandexRequest.newErrorInstance(
                            new Throwable(response.message())));
                }
            }

            @Override
            public void onFailure(@NonNull Call<TranslateResponse> call, @NonNull Throwable t) {
                setValue(YandexRequest.newErrorInstance(t));
            }
        });
    }

}
