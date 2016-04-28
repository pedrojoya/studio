package es.iessaladillo.pedrojoya.pr082;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

@SuppressWarnings("unused")
class EcoRequest extends StringRequest {

    // Constantes.
    private final static String URL_ECO = "http://www.informaticasaladillo.es/echo.php";

    // Variables.
    private final Map<String, String> params;

    // Constructor.
    public EcoRequest(Map<String, String> params, Listener<String> listener,
            ErrorListener errorListener) {
        super(Method.POST, URL_ECO, listener, errorListener);
        this.params = params;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
