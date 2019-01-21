package es.iessaladillo.pedrojoya.pr180.data.remote.echo;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr180.base.Resource;
import es.iessaladillo.pedrojoya.pr180.data.remote.HttpClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class EchoDataSourceImpl implements EchoDataSource {

    private static final String BASE_URL = "http://www.informaticasaladillo.es/echo.php";
    private static final String KEY_NAME = "nombre";
    private static final String KEY_DATE = "fecha";

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
        "dd/MM/yyyy " + "HH:mm:ss", Locale.getDefault());

    private final OkHttpClient okHttpClient;

    public EchoDataSourceImpl(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public LiveData<Resource<String>> requestEcho(String text, String tag) {
        MutableLiveData<Resource<String>> result = new MutableLiveData<>();
        result.postValue(Resource.loading());
        try {
            URL url = new URL(BASE_URL);
            RequestBody formBody = new FormBody.Builder().addEncoded(KEY_NAME, text).addEncoded(
                KEY_DATE, simpleDateFormat.format(new Date())).build();
            Request request = new Request.Builder().url(url).post(formBody).tag(tag).build();
            Call echoCall = okHttpClient.newCall(request);
            echoCall.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    result.postValue(Resource.error(e));
                }

                @Override
                public void onResponse(@NonNull Call call,
                    @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        ResponseBody responseBody = response.body();
                        if (responseBody != null) {
                            String content = responseBody.string().trim();
                            result.postValue(Resource.success(content));
                        }
                    } else {
                        result.postValue(Resource.error(new Exception(response.message())));
                    }
                }
            });
        } catch (Exception e) {
            result.postValue(Resource.error(e));
        }
        return result;
    }

    @Override
    public void cancel(String tag) {
        HttpClient.cancelCallsWithTag(okHttpClient, tag);
    }

}
