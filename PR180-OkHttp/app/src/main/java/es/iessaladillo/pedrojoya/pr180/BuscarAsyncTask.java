package es.iessaladillo.pedrojoya.pr180;

import android.os.AsyncTask;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

// Tarea asincrona que busca un nombre en Google.
class BuscarAsyncTask extends AsyncTask<String, Void, String> {

    private final OkHttpClient mOkHttpClient;
    private Call mOkHttpCall;

    // Interfaz de comunicación con la actividad.
    @SuppressWarnings("UnusedParameters")
    public interface Callbacks {
        void onPostExecute(BuscarAsyncTask object, String result);
    }

    // Variables.
    private Callbacks listener;

    // Constructor.
    public BuscarAsyncTask(Callbacks listener) {
        this.listener = listener;
        mOkHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

    // Realiza el procesamiento en segundo plano.
    @Override
    protected String doInBackground(String... params) {
        String resultado = "";
        // Se obtiene el nombre a buscar.
        String nombre = params[0];
        try {
            // Se obtiene la url de búsqueda.
            URL url = new URL("https://www.google.es/search?hl=es&q=\""
                    + URLEncoder.encode(nombre, "UTF-8") + "\"");
            Request request = new Request.Builder()
                    .url(url)
                    .header("User-Agent",
                            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)")
                    .build();
            mOkHttpCall = mOkHttpClient.newCall(request);
            Response response = mOkHttpCall.execute();
            if (response.isSuccessful()) {
                String contenido = response.body().string();
                // Se busca en el contenido la palabra Aproximadamente.
                resultado = extractResultado(contenido);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    private String extractResultado(String contenido) {
        String resultado = "";
        int ini = contenido.indexOf("Aproximadamente");
        if (ini != -1) {
            // Se busca el siguiente espacio en blanco después de
            // Aproximadamente.
            int fin = contenido.indexOf(" ", ini + 16);
            // El resultado corresponde a lo que sigue a
            // Aproximadamente, hasta el siguiente espacio en blanco.
            resultado = contenido.substring(ini + 16, fin);
        }
        return resultado;
    }

    // Una vez finalizado el procesamiento en segundo plano.
    @Override
    protected void onPostExecute(String result) {
        if (listener != null) {
            listener.onPostExecute(this, result);
        }
    }

    // Al ser cancelada la tarea.
    @Override
    protected void onCancelled() {
        if (mOkHttpCall != null) {
            mOkHttpCall.cancel();
        }
        // Se libera el listener.
        listener = null;
    }

}
