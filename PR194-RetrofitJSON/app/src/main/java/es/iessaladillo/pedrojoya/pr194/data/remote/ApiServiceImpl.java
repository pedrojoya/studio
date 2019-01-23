package es.iessaladillo.pedrojoya.pr194.data.remote;

import android.text.TextUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr194.base.Resource;
import es.iessaladillo.pedrojoya.pr194.data.remote.dto.StudentDto;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

public class ApiServiceImpl implements ApiService {

    private static ApiServiceImpl instance;
    private final OkHttpClient okHttpClient;
    private final Api api;

    private ApiServiceImpl(Api api, OkHttpClient okHttpClient) {
        this.api = api;
        this.okHttpClient = okHttpClient;
    }

    public static synchronized ApiServiceImpl getInstance(Api api, OkHttpClient okHttpClient) {
        if (instance == null) {
            synchronized (ApiServiceImpl.class) {
                if (instance == null) {
                    instance = new ApiServiceImpl(api, okHttpClient);
                }
            }
        }
        return instance;
    }

    private void cancelCallsWithTag(String tag) {
        for (okhttp3.Call call : okHttpClient.dispatcher().queuedCalls()) {
            String[] tagsArray = (String[]) call.request().tag();
            if (tagsArray != null) {
                String requestTag = tagsArray[0];
                if (TextUtils.equals(requestTag, tag)) call.cancel();
            }
        }
        for (okhttp3.Call call : okHttpClient.dispatcher().runningCalls()) {
            String[] tagsArray = (String[]) call.request().tag();
            if (tagsArray != null) {
                String requestTag = tagsArray[0];
                if (TextUtils.equals(requestTag, tag)) call.cancel();
            }
        }
    }

    @Override
    public LiveData<Resource<List<StudentDto>>> getStudents(String tag) {
        MutableLiveData<Resource<List<StudentDto>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());
        Call<List<StudentDto>> studentsCall = api.getStudents();
        String[] tagsArray = (String[]) studentsCall.request().tag();
        if (tagsArray != null) tagsArray[0] = tag;
        studentsCall.enqueue(new retrofit2.Callback<List<StudentDto>>() {
            @Override
            public void onResponse(@NonNull Call<List<StudentDto>> call,
                @NonNull Response<List<StudentDto>> response) {
                if (response.body() != null && response.isSuccessful()) {
                    result.setValue(Resource.success(response.body()));
                } else {
                    result.setValue(Resource.error(new Exception(response.message())));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<StudentDto>> call, @NonNull Throwable t) {
                result.setValue(Resource.error(new Exception(t.getMessage())));
            }
        });
        return result;
    }

    @Override
    public void cancel(String tag) {
        cancelCallsWithTag(tag);
    }


    public interface Api {

        @GET("students")

        Call<List<StudentDto>> getStudents();

    }

}
