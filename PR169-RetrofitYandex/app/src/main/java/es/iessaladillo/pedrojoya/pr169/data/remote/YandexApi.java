package es.iessaladillo.pedrojoya.pr169.data.remote;

import es.iessaladillo.pedrojoya.pr169.data.models.TranslateResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YandexApi {

    @SuppressWarnings("SameParameterValue")
    @GET("translate")
    Call<TranslateResponse> getTranslation(
            @Query(Constants.PARAM_API_KEY) String key,
            @Query(Constants.PARAM_TEXT) String text,
            @Query(Constants.PARAM_LANG) String lang);

}
