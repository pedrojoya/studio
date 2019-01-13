package es.iessaladillo.pedrojoya.pr195.data.remote;

import android.content.Context;

import com.ashokvarma.gander.GanderInterceptor;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.List;

import es.iessaladillo.pedrojoya.pr195.data.remote.dto.StudentDto;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class ApiServiceImpl implements ApiService {

    private static final String BASE_URL = "http://my-json-server.typicode.com/pedrojoya/jsonserver/";

    private static ApiServiceImpl instance;
    private Api api;

    private ApiServiceImpl(Api api) {
        this.api = api;
    }

    public static synchronized ApiServiceImpl getInstance(Api api) {
        if (instance == null) {
            synchronized (ApiServiceImpl.class) {
                if (instance == null) {
                    instance = new ApiServiceImpl(api);
                }
            }
        }
        return instance;
    }

    // For demo purposes
    @SuppressWarnings("unused")
    private void buildAPIService(Context context) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(new StethoInterceptor())
            .addInterceptor(new GanderInterceptor(context));
        OkHttpClient okHttpClient = builder.build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        api = retrofit.create(Api.class);
    }

    @Override
    public Single<List<StudentDto>> getStudents() {
        return api.getStudents().subscribeOn(Schedulers.io());
    }

    public interface Api {

        @GET("students")
        Single<List<StudentDto>> getStudents();

    }

}
