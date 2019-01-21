package es.iessaladillo.pedrojoya.pr040.data.remote;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr040.base.Resource;
import es.iessaladillo.pedrojoya.pr040.base.http.HttpCall;
import es.iessaladillo.pedrojoya.pr040.base.http.HttpCallback;
import es.iessaladillo.pedrojoya.pr040.base.http.HttpClient;
import es.iessaladillo.pedrojoya.pr040.base.http.HttpRequest;
import es.iessaladillo.pedrojoya.pr040.base.http.HttpResponse;
import es.iessaladillo.pedrojoya.pr040.data.remote.dto.StudentDto;

public class ApiServiceImpl implements ApiService {

    private static final String BASE_URL = "http://my-json-server.typicode.com/pedrojoya/jsonserver/";
    private static final int TIMEOUT = 5000;

    private final Type studentListType = new TypeToken<List<StudentDto>>() { }.getType();
    private final Gson gson = new Gson();
    private final HttpClient httpClient;

    public ApiServiceImpl(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public LiveData<Resource<List<StudentDto>>> getStudents(String tag) {
        MutableLiveData<Resource<List<StudentDto>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());
        URL url;
        try {
            url = URI.create(BASE_URL + "students").toURL();
        } catch (MalformedURLException e) {
            result.setValue(Resource.error(e));
            return result;
        }
        HttpRequest httpRequest = new HttpRequest.Builder(url).setTimeout(TIMEOUT)
            .setTag(tag)
            .build();
        HttpCall studentsCall = httpClient.newCall(httpRequest);
        studentsCall.enqueue(new HttpCallback() {
            @Override
            public void onFailure(HttpCall httpCall, IOException exception) {
                result.setValue(Resource.error(exception));
            }

            @Override
            public void onResponse(HttpCall httpCall, HttpResponse httpResponse) {
                if (httpResponse.getCode() == HttpURLConnection.HTTP_OK) {
                    try {
                        String content = new String(httpResponse.getBody());
                        List<StudentDto> studentDtoList = gson.fromJson(content, studentListType);
                        result.setValue(Resource.success(studentDtoList));
                    } catch (Exception e) {
                        result.setValue(Resource.error(e));
                    }
                } else {
                    result.setValue(Resource.error(new Exception(httpResponse.getMessage())));
                }
            }
        });
        return result;
    }

    @Override
    public void cancel(String tag) {
        httpClient.cancel(tag);
    }

}
