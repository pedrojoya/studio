package es.iessaladillo.pedrojoya.pr082.data.remote.search;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

class SearchRequest extends StringRequest {

    public static final String SEARCH_TAG = "SEARCH_TAG";

    public SearchRequest(String nombre, Listener<String> listener,
            ErrorListener errorListener) {
        super(Method.GET, "https://www.google.es/search?hl=es&q=\""
                + nombre + "\"", listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() {
        Map<String, String> params = new HashMap<>();
        params.put("User-Agent",
                "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
        return params;
    }

    @Override
    public Object getTag() {
        return SEARCH_TAG;
    }

}
