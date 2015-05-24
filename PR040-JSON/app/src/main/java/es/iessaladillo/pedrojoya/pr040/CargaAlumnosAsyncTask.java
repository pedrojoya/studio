package es.iessaladillo.pedrojoya.pr040;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CargaAlumnosAsyncTask extends AsyncTask<String, Void, String> {

    // Interfaz de comunicación con la actividad.
    public interface Callbacks {
        void onPostExecute(CargaAlumnosAsyncTask object, String result);
    }

    // Variables.
    Callbacks listener;

    // Constructor.
    public CargaAlumnosAsyncTask(Callbacks listener) {
        this.listener = listener;
    }

    // Realiza el procesamiento en segundo plano.
    @Override
    protected String doInBackground(String... params) {
        String contenido = "";
        // Se obtiene la url a obtener.
        String sUrl = params[0];
        try {
            // Se obtiene la url de búsqueda.
            URL url = new URL(sUrl);
            // Se crea la conexión.
            HttpURLConnection conexion = (HttpURLConnection) url
                    .openConnection();
            // Se establece el método de conexión.
            conexion.setRequestMethod("GET");
            // Se indica que pretendemos leer del flujo de entrada.
            conexion.setDoInput(true);
            // Se realiza la conexión.
            conexion.connect();
            // Si el código de respuesta es correcto.
            if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Se crea un lector que lee del flujo de entrada de la
                // conexión.
                BufferedReader lector = new BufferedReader(
                        new InputStreamReader(conexion.getInputStream()));
                // Se lee línea a línea y se agrega a la cadena contenido.
                String linea = lector.readLine();
                while (linea != null) {
                    contenido += linea;
                    linea = lector.readLine();
                }
                lector.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Se retorna el contenido.
        return contenido;
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
        // Se libera el listener.
        listener = null;
    }

}
