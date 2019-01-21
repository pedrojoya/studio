package es.iessaladillo.pedrojoya.pr082.data.remote.echo;

import com.android.volley.RequestQueue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr082.base.Event;
import es.iessaladillo.pedrojoya.pr082.base.Resource;

public class EchoDataSourceImpl implements EchoDataSource {

    private static final String KEY_NAME = "nombre";
    private static final String KEY_DATE = "fecha";

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
        "dd/MM/yyyy " + "HH:mm:ss", Locale.getDefault());

    private final RequestQueue requestQueue;

    public EchoDataSourceImpl(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public LiveData<Resource<Event<String>>> requestEcho(String text) {
        MutableLiveData<Resource<Event<String>>> result = new MutableLiveData<>();
        try {
            result.postValue(Resource.loading());
            Map<String, String> params = new HashMap<>();
            params.put(KEY_NAME, text);
            params.put(KEY_DATE, simpleDateFormat.format(new Date()));
            requestQueue.add(new EchoRequest(params,
                response -> result.postValue(Resource.success(new Event<>(response.trim()))),
                volleyError -> result.postValue(
                    Resource.error(new Exception(volleyError.getMessage())))));
        } catch (Exception e) {
            result.postValue(Resource.error(e));
        }
        return result;
    }

}
