package es.iessaladillo.pedrojoya.pr083;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

@SuppressWarnings("WeakerAccess")
public class App extends Application {

    private static RequestQueue colaPeticiones;

    @Override
    public void onCreate() {
        super.onCreate();
        // Se crea la cola de peticiones de Volley.
        colaPeticiones = Volley.newRequestQueue(this);
    }

    // Retorna la cola de peticiones de Volley.
    public static RequestQueue getRequestQueue() {
        if (colaPeticiones != null) {
            return colaPeticiones;
        } else {
            throw new IllegalStateException("RequestQueue no inicializada");
        }
    }

}
