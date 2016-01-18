package es.iessaladillo.pedrojoya.pr083;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    // Constantes.
    private static final String URL_DATOS =
            "https://dl.dropboxusercontent.com/u/67422/Android/json/datos.json";
    private static final String STATE_LISTA = "state_lista";
    private static final String STATE_DATOS = "state_datos";

    private SwipeRefreshLayout swlPanel;
    private RequestQueue colaPeticiones;
    private LinearLayoutManager mLayoutManager;
    private AlumnosAdapter mAdaptador;
    private TextView lblEmpty;
    private Parcelable mEstadoLista;
    private ArrayList<Alumno> mDatos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtiene la cola de peticiones de Volley.
        colaPeticiones = App.getRequestQueue();
        // Si venimos de estado anterior recuperamos datos.
        if (savedInstanceState != null) {
            mDatos = savedInstanceState.getParcelableArrayList(STATE_DATOS);
        }
        else {
            mDatos = new ArrayList<>();
        }
        initVistas();
        lblEmpty.setVisibility(mAdaptador.isEmpty()?View.VISIBLE:View.INVISIBLE);
        /* Si queremos que se ejecute automáticamente inicialmente */
        if (savedInstanceState ==  null) {
            // Si hay conexión a Internet.
            if (isConnectionAvailable()) {
                swlPanel.post(new Runnable() {
                    @Override
                    public void run() {
                        swlPanel.setRefreshing(true);
                    }
                });
                // realizarPeticionJSON();
                realizarPeticionGson();
            } else {
                mostrarToast(getString(R.string.no_hay_conexion_a_internet));
            }
        }

    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        configToolbar();
        configRecyclerView();
        configSwipeRefresh();
    }

    // Configura la Toolbar.
    private void configToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    // Configura el RecyclerView.
    private void configRecyclerView() {
        lblEmpty = (TextView) findViewById(R.id.lblEmpty);
        RecyclerView lstAlumnos = (RecyclerView) findViewById(R.id.lstAlumnos);
        lstAlumnos.setHasFixedSize(true);
        mAdaptador = new AlumnosAdapter(mDatos);
        mAdaptador.setOnEmptyStateChangedListener(new AlumnosAdapter.OnEmptyStateChangedListener() {
            @Override
            public void onEmptyStateChanged(boolean isEmpty) {
                lblEmpty.setVisibility(isEmpty ? View.VISIBLE: View.INVISIBLE);
            }
        });
        lstAlumnos.setAdapter(mAdaptador);
        mLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        lstAlumnos.setLayoutManager(mLayoutManager);
        lstAlumnos.setItemAnimator(new DefaultItemAnimator());
    }

    // Configura el SwipeRefreshLayout.
    private void configSwipeRefresh() {
        swlPanel = (SwipeRefreshLayout) findViewById(R.id.swlPanel);
        swlPanel.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swlPanel.setOnRefreshListener(this);
    }

    // Añade a la cola de peticiones una petición JSON.
    private void realizarPeticionJSON() {
        // Se crea el listener para la respuesta.
        Listener<JSONArray> listener = new Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                // Se oculta el círculo de progreso.
                swlPanel.setRefreshing(false);
                // Se procesa la cadena JSON, obteniendo el ArrayList de
                // alumnos.
                List<Alumno> alumnos = procesarJSON(response);
                // Se cargan los alumnos en la lista.
                cargarAlumnos(alumnos);
            }
        };
        // Se crea el listener de error.
        ErrorListener errorListener = new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                mostrarToast(error.getMessage());
            }
        };
        // Se crea la petición.
        JsonArrayRequest peticion = new JsonArrayRequest(URL_DATOS, listener,
                errorListener);
        // Se ñade la petición a la cola de Volley.
        colaPeticiones.add(peticion);
    }

    // Añade a la cola de peticiones una petición Gson.
    private void realizarPeticionGson() {
        // Se crea el listener para la respuesta.
        Listener<ArrayList<Alumno>> listener = new Listener<ArrayList<Alumno>>() {

            @Override
            public void onResponse(ArrayList<Alumno> response) {
                // Se oculta el círculo de progreso.
                swlPanel.setRefreshing(false);
                // Se cargan los alumnos en la lista.
                cargarAlumnos(response);
            }
        };
        // Se crea el listener de error.
        ErrorListener errorListener = new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                mostrarToast(error.getMessage());
            }
        };
        // Se crea la petición.
        Gson gson = new Gson();
        Type tipo = new TypeToken<List<Alumno>>() {
        }.getType();

        GsonArrayRequest<ArrayList<Alumno>> peticion = new GsonArrayRequest<>(
                Request.Method.GET, URL_DATOS, tipo, listener, errorListener,
                gson);
        // Se añade la petición a la cola de Volley.
        colaPeticiones.add(peticion);
    }

    // Carga los alumnos en el ListView. Recibe la lista de alumnos.
    private void cargarAlumnos(List<Alumno> alumnos) {
        // Se cambian los datos del adaptador.
        mAdaptador.swapData(new ArrayList<>(alumnos));
    }

    // Procesa la cadena JSON y retorna el ArrayList de alumnos.
    private List<Alumno> procesarJSON(JSONArray result) {
        // Se crea el ArrayList a retornar.
        List<Alumno> alumnos = new ArrayList<>();
        try {
            // Por cada objeto JSON del array JSON
            for (int i = 0; i < result.length(); i++) {
                // Se obtiene el objeto JSON correspondiente al alumno.
                JSONObject alumnoJSON = result.getJSONObject(i);
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
            // realizarPeticionJSON();
            realizarPeticionGson();
        } else {
            mostrarToast(getString(R.string.no_hay_conexion_a_internet));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Se salva el estado del RecyclerView.
        mEstadoLista = mLayoutManager.onSaveInstanceState();
        outState.putParcelable(STATE_LISTA, mEstadoLista);
        outState.putParcelableArrayList(STATE_DATOS, mAdaptador.getData());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Se obtiene el estado anterior de la lista.
        mEstadoLista = savedInstanceState.getParcelable(STATE_LISTA);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Se retaura el estado de la lista.
        if (mEstadoLista != null) {
            mLayoutManager.onRestoreInstanceState(mEstadoLista);
        }
    }

}