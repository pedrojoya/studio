package es.iessaladillo.pedrojoya.pr199.dagger;

import android.content.Context;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.readystatesoftware.chuck.ChuckInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.iessaladillo.pedrojoya.pr199.api.Constants;
import es.iessaladillo.pedrojoya.pr199.api.YandexAPI;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings({"WeakerAccess", "unused"})
@Module
public class NetModule {

    @Singleton
    @Provides
    public static HttpLoggingInterceptor providesHttpLoggingInterceptor() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logInterceptor;
    }

    @Singleton
    @Provides
    public static StethoInterceptor providesStethoInterceptor(Context context) {
        Stetho.initializeWithDefaults(context);
        return new StethoInterceptor();
    }

    @Singleton
    @Provides
    public static ChuckInterceptor providesChuckInterceptor(Context context) {
        return new ChuckInterceptor(context);
    }

    @Singleton
    @Provides
    public static OkHttpClient providesOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor,
            StethoInterceptor stethoInterceptor, ChuckInterceptor chuckInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(httpLoggingInterceptor);
        builder.addInterceptor(stethoInterceptor);
        builder.addInterceptor(chuckInterceptor);
        return builder.build();
    }

    @Singleton
    @Provides
    public static YandexAPI providesYandexAPI(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(YandexAPI.class);
    }

}
