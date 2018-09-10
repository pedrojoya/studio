package es.iessaladillo.pedrojoya.pr140.data.remote;


import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.readystatesoftware.chuck.ChuckInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ApiService {

    private static final String BASE_URL =
            "http://informaticasaladillo.es/android/elecciones/";

    private static ApiService instance;
    private final Api api;

    public static synchronized ApiService getInstance(Context context) {
        if (instance == null) {
            synchronized (ApiService.class) {
                if (instance == null) {
                    instance = new ApiService(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    private ApiService(Context context) {
        api = buildAPIService(context);
    }

    private Api buildAPIService(Context context) {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor().setLevel(
                HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logInterceptor)
                .addInterceptor(new StethoInterceptor())
                .addInterceptor(new ChuckInterceptor(context))
                .build();
        @SuppressWarnings("deprecation")
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
                SimpleXmlConverterFactory.create()).client(client).build();
        return retrofit.create(Api.class);
    }

    public Api getApi() {
        return api;
    }

}
