package es.iessaladillo.pedrojoya.pr083.data.remote;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyInstance {

    private static VolleyInstance instance;
    private final RequestQueue requestQueue;

    private VolleyInstance(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized VolleyInstance getInstance(Context context) {
        if (instance == null) {
            instance = new VolleyInstance(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

}
