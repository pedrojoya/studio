package es.iessaladillo.pedrojoya.pr082.data.remote;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

public class EchoRequest extends StringRequest {

    private final static String URL_ECO = "http://www.informaticasaladillo.es/echo.php";

    private final Map<String, String> params;

    public EchoRequest(Map<String, String> params, Listener<String> listener,
            ErrorListener errorListener) {
        super(Method.POST, URL_ECO, listener, errorListener);
        this.params = params;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

}
