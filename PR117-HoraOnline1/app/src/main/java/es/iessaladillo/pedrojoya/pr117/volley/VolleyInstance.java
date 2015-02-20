package es.iessaladillo.pedrojoya.pr117.volley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

// Clase singleton para Volley.
public class VolleyInstance {

    private static VolleyInstance mVolley = null;
    private RequestQueue mRequestQueue;

    // Constructor privado (no será posible usar new).
    private VolleyInstance(Context context) {
        // Crea la cola de peticiones (en el contexto global).
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    // Método factoría que construye el objeto VolleyInstance.
    public static VolleyInstance getInstance(Context context) {
        // Si no existe ya la instancia, se crea.
        if (mVolley == null) {
            mVolley = new VolleyInstance(context);
        }
        return mVolley;
    }

    // Retorna la cola de peticiones.
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

}