package es.iessaladillo.pedrojoya.pr180;

import android.os.AsyncTask;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

// Tarea asíncrona que envía datos a un script php y lee el texto retornado.
class EcoAsyncTask extends AsyncTask<String, Void, String> {

    // Constantes.
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_FECHA = "fecha";

    private final OkHttpClient mOkHttpClient;
    private Call mOkHttpCall;

    // Interfaz de comunicación con la actividad.
    @SuppressWarnings("UnusedParameters")
    public interface Callbacks {
        void onPostExecute(EcoAsyncTask objeto, String result);
    }

    // Variables.
    private Callbacks listener;
    private final SimpleDateFormat formateador;

    // Constructor. Recibe el objeto que actuará de listener.
    public EcoAsyncTask(Callbacks listener) {
        this.listener = listener;
        formateador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss",
                Locale.getDefault());
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
            URL url = new URL("http://www.informaticasaladillo.es/echo.php");
            RequestBody formBody = new FormBody.Builder()
                    .addEncoded(KEY_NOMBRE, nombre)
                    .addEncoded(KEY_FECHA, formateador.format(new Date()))
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
            mOkHttpCall = mOkHttpClient.newCall(request);
            Response response = mOkHttpCall.execute();
            if (response.isSuccessful()) {
                resultado = response.body().string().trim();
            }
        } catch (IOException e) {
            e.printStackTrace();
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
