package es.iessaladillo.pedrojoya.pr082.data.remote.echo;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

class EchoRequest extends StringRequest {

    private final static String URL_ECO = "http://www.informaticasaladillo.es/echo.php";
    public static final String ECHO_TAG = "ECHO_TAG";

    private final Map<String, String> params;

    public EchoRequest(Map<String, String> params, Listener<String> listener,
            ErrorListener errorListener) {
        super(Method.POST, URL_ECO, listener, errorListener);
        this.params = params;
    }

    @Override
    protected Map<String, String> getParams() {
        return params;
    }

    @Override
    public Object getTag() {
        return ECHO_TAG;
    }

}
