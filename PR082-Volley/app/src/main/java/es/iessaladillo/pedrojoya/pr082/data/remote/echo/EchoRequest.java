package es.iessaladillo.pedrojoya.pr082.data.remote.echo;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

class EchoRequest extends StringRequest {

    private final static String BASE_URL = "http://www.informaticasaladillo.es/echo.php";

    private final Map<String, String> params;

    EchoRequest(Map<String, String> params, Listener<String> listener, ErrorListener errorListener) {
        super(Method.POST, BASE_URL, listener, errorListener);
        this.params = params;
    }

    @Override
    protected Map<String, String> getParams() {
        return params;
    }

}
