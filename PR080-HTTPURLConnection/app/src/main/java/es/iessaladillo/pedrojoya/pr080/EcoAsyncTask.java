package es.iessaladillo.pedrojoya.pr080;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// Tarea asíncrona que envía datos a un script php y lee el texto retornado.
class EcoAsyncTask extends AsyncTask<String, Void, String> {

    // Constantes.
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_FECHA = "fecha";

    // Interfaz de comunicación con la actividad.
    @SuppressWarnings("UnusedParameters")
    public interface Callbacks {
        void onPostExecute(EcoAsyncTask objeto, String result);
    }

    // Variables.
    private Callbacks listener;
    private final SimpleDateFormat formateador;

    // Constructor. Recibe el objeto que actuar� de listener.
    public EcoAsyncTask(Callbacks listener) {
        this.listener = listener;
        formateador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss",
                Locale.getDefault());
    }

    // Realiza el procesamiento en segundo plano.
    @Override
    protected String doInBackground(String... params) {
        String resultado = "";
        // Se obtiene el nombre a buscar.
        String nombre = params[0];
        try {
            // Se obtiene la url de b�squeda.
            URL url = new URL("http://www.informaticasaladillo.es/echo.php");
            // Se crea la conexi�n.
            HttpURLConnection conexion = (HttpURLConnection) url
                    .openConnection();
            // Se establece el m�todo de conexi�n.
            conexion.setRequestMethod("POST");
            // Se indica que pretendemos escribir en el flujo de salida y leer
            // del flujo de entrada.
            conexion.setDoOutput(true);
            conexion.setDoInput(true);
            // Se crea un escritor que escriba en el flujo de salida de la
            // conexi�n.
            PrintWriter escritor = new PrintWriter(conexion.getOutputStream());
            // Se escriben los par�metros en el flujo de salida.
            escritor.write(KEY_NOMBRE + "="
                    + URLEncoder.encode(nombre, "UTF-8"));
            escritor.write("&"
                    + KEY_FECHA
                    + "="
                    + URLEncoder.encode(formateador.format(new Date()), "UTF-8"));
            // Se env�an los datos.
            escritor.flush();
            // Se cierra el flujo de salida.
            escritor.close();
            // Si el c�digo de respuesta es correcto.
            if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Se crea un lector que lee del flujo de entrada de la
                // conexi�n.
                BufferedReader lector = new BufferedReader(
                        new InputStreamReader(conexion.getInputStream()));
                // Se lee l�nea a l�nea y se agrega a la cadena contenido.
                String linea = lector.readLine();
                while (linea != null) {
                    resultado += linea;
                    linea = lector.readLine();
                }
                lector.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Retorna el texto obtenido, que es recibido por onPostExecute().
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
