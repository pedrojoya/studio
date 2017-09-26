package es.iessaladillo.pedrojoya.pr169.data.remote;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.readystatesoftware.chuck.ChuckInterceptor;

import es.iessaladillo.pedrojoya.pr169.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YandexService {

    private static YandexService instance;
    private YandexApi api;

    public static synchronized YandexService getInstance(Context context) {
        if (instance == null) {
            instance = new YandexService(context.getApplicationContext());
        }
        return instance;
    }

    private YandexService(Context context) {
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
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(YandexApi.class);
    }

    @SuppressWarnings("unused")
    private void addApiKey(OkHttpClient.Builder builder) {
        builder.addInterceptor(chain -> {
            Request request = chain.request();
            HttpUrl newUrl = request.url().newBuilder().addQueryParameter(
                    Constants.PARAM_API_KEY, BuildConfig.YANDEX_API_KEY).build();
            Request newRequest;
            newRequest = request.newBuilder()
                    .url(newUrl)
                    .build();
            return chain.proceed(newRequest);
        });
    }

    public YandexApi getApi() {
        return api;
    }

}
