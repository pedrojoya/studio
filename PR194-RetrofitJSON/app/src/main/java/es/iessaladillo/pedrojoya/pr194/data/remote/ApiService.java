package es.iessaladillo.pedrojoya.pr194.data.remote;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.readystatesoftware.chuck.ChuckInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private static final String BASE_URL = "http://www.informaticasaladillo.es/";

    private static ApiService instance;
    private Api api;

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
        buildAPIService(context);
    }

    private void buildAPIService(Context context) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(httpLoggingInterceptor);
        builder.addInterceptor(new StethoInterceptor());
        builder.addInterceptor(new ChuckInterceptor(context));
        OkHttpClient okHttpClient = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    public Api getApi() {
        return api;
    }

}
