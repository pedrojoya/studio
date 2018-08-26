package es.iessaladillo.pedrojoya.pr082.data.remote;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyInstance {

    private static VolleyInstance instance;
    private final RequestQueue requestQueue;

    private VolleyInstance(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static VolleyInstance getInstance(Context context) {
        if (instance == null) {
            synchronized (VolleyInstance.class) {
                if (instance == null) {
                    instance = new VolleyInstance(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

}
