package es.iessaladillo.pedrojoya.pr199.api;

import es.iessaladillo.pedrojoya.pr199.models.TranslateResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// Interfaz para Retrofit de acceso a la API de Yandex.
public interface YandexAPI {

    @SuppressWarnings("SameParameterValue")
    @GET("translate")
    Call<TranslateResponse> getTranslation(
            @Query(Constants.PARAM_API_KEY) String key,
            @Query(Constants.PARAM_TEXT) String text,
            @Query(Constants.PARAM_LANG) String lang);

}
