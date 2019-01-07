package es.iessaladillo.pedrojoya.pr040.data.remote;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import es.iessaladillo.pedrojoya.pr040.base.Call;
import es.iessaladillo.pedrojoya.pr040.base.Resource;
import es.iessaladillo.pedrojoya.pr040.data.remote.model.Student;
import es.iessaladillo.pedrojoya.pr040.utils.NetworkUtils;

public class ApiServiceImpl implements ApiService {

    private static final String BASE_URL = "http://my-json-server.typicode.com/pedrojoya/jsonserver/";
    private static final int TIMEOUT = 5000;

    @Override
    public Call<Resource<List<Student>>> getStudents() {
        return new Call<Resource<List<Student>>>() {
            @Override
            protected void doAsync() {
                postValue(Resource.loading());
                String content;
                try {
                    // Simulate latency
                    Thread.sleep(2000);
                    content = NetworkUtils.loadUrl(BASE_URL + "students", TIMEOUT);
                    postValue(Resource.success(parseWithGson(content)));
                } catch (Exception e) {
                    postValue(Resource.error(e));
                }
            }
        };
    }

    private ArrayList<Student> parseWithGson(String content) throws JsonSyntaxException {
        Gson gson = new Gson();
        Type studentListType = new TypeToken<List<Student>>() { }.getType();
        return gson.fromJson(content, studentListType);
    }

}
