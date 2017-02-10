package es.iessaladillo.pedrojoya.pr040;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

// Cargador que entrega una lista de alumnos, obteniéndolas
// en un hilo secundario a partir de petición HTTP con respuesta JSON.
class AlumnosLoader extends
        AsyncTaskLoader<ArrayList<Alumno>> {

    private static final String URL_DATOS =
            "https://dl.dropboxusercontent.com/u/67422/Android/json/datos.json";

    private ArrayList<Alumno> mDatos;

    public AlumnosLoader(Context context) {
        super(context);
    }

    // Cuando el cargador pasa al estado de iniciado.
    @Override
    protected void onStartLoading() {
        // Si se tienen datos o está activa la bandera de recarga, se recarga.
        if (mDatos == null || takeContentChanged()) {
            forceLoad();
        } else {
            // Como se tienen datos y no se ha activado recarga, se entregan
            deliverResult(mDatos);
        }
    }

    // Tarea de carga de datos en hilo secundario.
    @Override
    public ArrayList<Alumno> loadInBackground() {
        String contenido = "";
        // Se define la lista de cadenas que retornará la tarea.
        ArrayList<Alumno> datos = new ArrayList<>();
        // Se obtienen los datos.
        if (isConnectionAvailable()) {
            try {
                // Se obtiene la url de búsqueda.
                URL url = new URL(URL_DATOS);
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
                    if (!TextUtils.isEmpty(contenido)) {
                        // Se procesa la cadena JSON, obteniendo el ArrayList de alumnos.
                        // List<Alumno> alumnos = procesarJSON(contenido);
                        datos = procesarGSON(contenido);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Se retorna la lista de cadenas.
        return datos;
    }

    // Cuando se deben entregar los datos. Recibe los datos a entregar.
    @Override
    public void deliverResult(ArrayList<Alumno> datos) {
        // Cacheamos los datos por si nos los vuelven a pedir.
        mDatos = datos;
        // Se entregan los datos.
        super.deliverResult(datos);
    }

    // Procesa la cadena JSON y retorna el ArrayList de alumnos.
    @SuppressWarnings("unused")
    private ArrayList<Alumno> procesarJSON(String result) {
        // Se crea el ArrayList a retornar.
        ArrayList<Alumno> alumnos = new ArrayList<>();
        try {
            // Se obtiene el elemento raíz de la cadena JSON, que es un
            // JSONArray.
            JSONArray alumnosJSON = new JSONArray(result);
            // Por cada objeto JSON del array JSON
            for (int i = 0; i < alumnosJSON.length(); i++) {
                // Se obtiene el objeto JSON correspondiente al alumno.
                JSONObject alumnoJSON = alumnosJSON.getJSONObject(i);
                // Se crea un objeto alumno.
                Alumno alumno = new Alumno();
                // Se escriben las propiedades del alumno, obtenidas del objeto
                // JSON.
                alumno.setNombre(alumnoJSON.getString(Alumno.KEY_NOMBRE));
                alumno.setDireccion(alumnoJSON.getString(Alumno.KEY_DIRECCION));
                alumno.setTelefono(alumnoJSON.getString(Alumno.KEY_TELEFONO));
                alumno.setCurso(alumnoJSON.getString(Alumno.KEY_CURSO));
                alumno.setRepetidor(alumnoJSON.getBoolean(Alumno.KEY_REPETIDOR));
                alumno.setEdad(alumnoJSON.getInt(Alumno.KEY_EDAD));
                alumno.setFoto(alumnoJSON.getString(Alumno.KEY_FOTO));
                // Se añade el alumno al ArrayList.
                alumnos.add(alumno);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Se retorna el ArrayList.
        return alumnos;
    }

    // Procesa la cadena JSON y retorna el ArrayList de alumnos.
    private ArrayList<Alumno> procesarGSON(String result) {
        // Se crea el objeto Gson.
        Gson gson = new Gson();
        Type tipoListaAlumnos = new TypeToken<List<Alumno>>() {
        }.getType();
        // Se procesa la cadena JSON y se retorna.
        return gson.fromJson(result, tipoListaAlumnos);
    }

    // Retorna si hay conexión a la red o no.
    private boolean isConnectionAvailable() {
        // Se obtiene del gestor de conectividad la información de red.
        ConnectivityManager gestorConectividad =
                (ConnectivityManager) getContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        NetworkInfo infoRed = gestorConectividad.getActiveNetworkInfo();
        // Se retorna si hay conexión.
        return infoRed != null && infoRed.isConnected();
    }

}
