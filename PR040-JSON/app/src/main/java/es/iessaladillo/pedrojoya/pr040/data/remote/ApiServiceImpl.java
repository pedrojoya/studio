package es.iessaladillo.pedrojoya.pr040.data.remote;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr040.base.Resource;
import es.iessaladillo.pedrojoya.pr040.data.remote.model.Student;
import es.iessaladillo.pedrojoya.pr040.utils.NetworkUtils;

public class ApiServiceImpl implements ApiService {

    private static final String BASE_URL = "http://my-json-server.typicode.com/pedrojoya/jsonserver/";
    private static final int TIMEOUT = 5000;

    @Override
    public LiveData<Resource<List<Student>>> getStudents() {
        MutableLiveData<Resource<List<Student>>> result = new MutableLiveData<>();
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> {
            result.postValue(Resource.loading());
            String content;
            try {
                // Simulate latency
                Thread.sleep(2000);
                content = NetworkUtils.loadUrl(BASE_URL + "students", TIMEOUT);
                result.postValue(Resource.success(parseWithGson(content)));
            } catch (Exception e) {
                result.postValue(Resource.error(e));
            }
        });
        return result;

    }

    private ArrayList<Student> parseWithGson(String content) throws JsonSyntaxException {
        Gson gson = new Gson();
        Type studentListType = new TypeToken<List<Student>>() { }.getType();
        return gson.fromJson(content, studentListType);
    }

}
