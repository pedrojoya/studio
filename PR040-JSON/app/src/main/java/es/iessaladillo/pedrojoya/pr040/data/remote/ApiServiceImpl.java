package es.iessaladillo.pedrojoya.pr040.data.remote;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import es.iessaladillo.pedrojoya.pr040.data.remote.dto.StudentDto;
import es.iessaladillo.pedrojoya.pr040.utils.NetworkUtils;

public class ApiServiceImpl implements ApiService {

    private static final String BASE_URL = "http://my-json-server.typicode.com/pedrojoya/jsonserver/";
    private static final int TIMEOUT = 5000;

    private final Type studentListType = new TypeToken<List<StudentDto>>() { }.getType();
    private final Gson gson = new Gson();

    @Override
    public void getStudents(ApiService.Callback<List<StudentDto>> callback) {
        new CallTask<>(BASE_URL + "students", TIMEOUT, studentListType, callback).execute();
    }

    @SuppressWarnings("SameParameterValue")
    @SuppressLint("StaticFieldLeak")
    private class CallTask<T> extends AsyncTask<Void, Void, String> {

        private final ApiService.Callback<T> callback;
        private final String url;
        private final int timeout;
        private final Type type;
        private Exception exception;

        private CallTask(String url, int timeout, Type type, Callback<T> callback) {
            this.url = url;
            this.timeout = timeout;
            this.type = type;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String content;
            try {
                // Simulate latency
                Thread.sleep(5000);
                content = NetworkUtils.loadUrl(url, timeout);
                return content;
            } catch (Exception e) {
                exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(String content) {
            if (exception != null) {
                callback.onFailure(exception);
            } else {
                try {
                    T result = gson.fromJson(content, type);
                    callback.onResponse(result);
                } catch (Exception e) {
                    callback.onFailure(exception);
                }
            }
        }

    }

}
