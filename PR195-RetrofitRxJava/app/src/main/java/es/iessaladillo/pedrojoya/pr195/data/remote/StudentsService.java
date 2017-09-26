package es.iessaladillo.pedrojoya.pr195.data.remote;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.readystatesoftware.chuck.ChuckInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudentsService {

    private static final String BASE_URL = "http://www.informaticasaladillo.es/";

    private static StudentsService instance;
    private StudentsApi api;

    public static synchronized StudentsService getInstance(Context context) {
        if (instance == null) {
            instance = new StudentsService(context.getApplicationContext());
        }
        return instance;
    }

    private StudentsService(Context context) {
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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        api = retrofit.create(StudentsApi.class);
    }

    public StudentsApi getApi() {
        return api;
    }

}
