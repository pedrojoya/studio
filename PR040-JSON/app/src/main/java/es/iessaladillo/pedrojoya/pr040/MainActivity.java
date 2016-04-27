package es.iessaladillo.pedrojoya.pr040;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        CargaAlumnosAsyncTask.Callbacks, SwipeRefreshLayout.OnRefreshListener {

    // Constantes.
    private static final String URL_DATOS =
            "https://dl.dropboxusercontent.com/u/67422/Android/json/datos.json";

    // Vistas.
    private ListView lstAlumnos;

    // Variables.
    private CargaAlumnosAsyncTask tarea = null;
    private SwipeRefreshLayout swlPanel;

    // Al crearse la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
        // Si hay conexión a Internet.
        if (isConnectionAvailable()) {
            swlPanel.post(new Runnable() {
                @Override
                public void run() {
                    swlPanel.setRefreshing(true);
                }
            });
            // Se lanza la tarea para obtener los datos de los alumnos.
            lanzarTarea();
        } else {
            mostrarToast(getString(R.string.no_hay_conexion_a_internet));
        }
    }

    // Lanza la tarea asíncrona de carga de alumnos.
    private void lanzarTarea() {
        tarea = new CargaAlumnosAsyncTask(this);
        tarea.execute(URL_DATOS);
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        lstAlumnos = (ListView) findViewById(R.id.lstAlumnos);
        lstAlumnos.setEmptyView(findViewById(R.id.lblEmpty));
        swlPanel = (SwipeRefreshLayout) findViewById(R.id.swlPanel);
        if (swlPanel != null) {
            swlPanel.setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
            swlPanel.setOnRefreshListener(this);
        }
    }

    // Al ser pausada la actividad.
    @Override
    protected void onPause() {
        super.onPause();
        // Se cancela la tarea secundaria.
        if (tarea != null) {
            tarea.cancel(true);
            tarea = null;
        }
    }

    // Cuando se termina de ejecutar la tarea secundaria. Recibe la tarea y
    // la cadena JSON resultado.
    @Override
    public void onPostExecute(CargaAlumnosAsyncTask object, String result) {
        swlPanel.setRefreshing(false);
        // Se procesa la cadena JSON y se cargan los alumnos en la lista.
        if (!TextUtils.isEmpty(result)) {
            // Se procesa la cadena JSON, obteniendo el ArrayList de alumnos.
            // List<Alumno> alumnos = procesarJSON(result);
            List<Alumno> alumnos = procesarGSON(result);
            cargarAlumnos(alumnos);
        }

    }

    // Carga los alumnos en el ListView. Recibe la lista de alumnos.
    private void cargarAlumnos(List<Alumno> alumnos) {
        // Se crea y asigna el adaptador para el ListView.
        AlumnosAdapter adaptador = new AlumnosAdapter(this, new ArrayList<>(alumnos));
        lstAlumnos.setAdapter(adaptador);
    }

    // Procesa la cadena JSON y retorna el ArrayList de alumnos.
    @SuppressWarnings("unused")
    private List<Alumno> procesarJSON(String result) {
        // Se crea el ArrayList a retornar.
        List<Alumno> alumnos = new ArrayList<>();
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
    private List<Alumno> procesarGSON(String result) {
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
        ConnectivityManager gestorConectividad = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infoRed = gestorConectividad.getActiveNetworkInfo();
        // Se retorna si hay conexión.
        return (infoRed != null && infoRed.isConnected());
    }

    // Muestra un toast con duración larga.
    private void mostrarToast(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onRefresh() {
        // Si hay conexión a Internet.
        if (isConnectionAvailable()) {
            // Se lanza la tarea para obtener los datos de los alumnos.
            lanzarTarea();
        } else {
            mostrarToast(getString(R.string.no_hay_conexion_a_internet));
        }
    }

}