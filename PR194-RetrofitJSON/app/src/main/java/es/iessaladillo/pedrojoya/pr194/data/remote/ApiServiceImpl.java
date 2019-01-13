package es.iessaladillo.pedrojoya.pr194.data.remote;

import android.content.Context;

import com.ashokvarma.gander.GanderInterceptor;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.List;

import androidx.annotation.NonNull;
import es.iessaladillo.pedrojoya.pr194.data.remote.dto.StudentDto;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
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
    public void getStudents(Callback<List<StudentDto>> callback) {
        api.getStudents().enqueue(new retrofit2.Callback<List<StudentDto>>() {
            @Override
            public void onResponse(@NonNull Call<List<StudentDto>> call,
                @NonNull Response<List<StudentDto>> response) {
                if (response.body() != null && response.isSuccessful()) {
                    callback.onResponse(response.body());
                } else {
                    callback.onFailure(new Exception(response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<StudentDto>> call, @NonNull Throwable t) {
                callback.onFailure(new Exception(t.getMessage()));
            }
        });
    }

    public interface Api {

        @GET("students")
        Call<List<StudentDto>> getStudents();

    }

}
