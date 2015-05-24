package es.iessaladillo.pedrojoya.pr080;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

// Tarea asincrona que busca un nombre en Google.
class BuscarAsyncTask extends AsyncTask<String, Void, String> {

    // Interfaz de comunicación con la actividad.
    public interface Callbacks {
        void onPostExecute(BuscarAsyncTask object, String result);
    }

    // Variables.
    private Callbacks listener;

    // Constructor.
    public BuscarAsyncTask(Callbacks listener) {
        this.listener = listener;
    }

    // Realiza el procesamiento en segundo plano.
    @Override
    protected String doInBackground(String... params) {
        String resultado = "";
        String contenido = "";
        // Se obtiene el nombre a buscar.
        String nombre = params[0];
        try {
            // Se obtiene la url de búsqueda.
            URL url = new URL("https://www.google.es/search?hl=es&q=\""
                    + URLEncoder.encode(nombre, "UTF-8") + "\"");
            // Se crea la conexión.
            HttpURLConnection conexion = (HttpURLConnection) url
                    .openConnection();
            // Se establece el método de conexión.
            conexion.setRequestMethod("GET");
            // Google exige identificar el user agent.
            conexion.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
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
                // Se busca en el contenido la palabra Aproximadamente.
                int ini = contenido.indexOf("Aproximadamente");
                if (ini != -1) {
                    // Se busca el siguiente espacio en blanco después de
                    // Aproximadamente.
                    int fin = contenido.indexOf(" ", ini + 16);
                    // El resultado corresponde a lo que sigue a
                    // Aproximadamente, hasta el siguiente espacio en blanco.
                    resultado = contenido.substring(ini + 16, fin);
                }
            }
        } catch (Exception e) {
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
        // Se libera el listener.
        listener = null;
    }

}
