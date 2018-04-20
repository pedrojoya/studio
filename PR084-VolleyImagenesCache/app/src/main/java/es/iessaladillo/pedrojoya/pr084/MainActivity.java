package es.iessaladillo.pedrojoya.pr084;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private FotosAdapter mAdaptador;
    private RequestQueue mColaPeticiones;
    private String mUrlSiguiente;
    private SwipeRefreshLayout swlPanel;
    private EndlessRecyclerOnScrollListener mEndlessScrollListener;

    // Al crearse la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
        // Se obtiene la cola de peticiones de Volley.
        mColaPeticiones = App.getRequestQueue();
        // Se cargan los datos iniciales.
        swlPanel.post(new Runnable() {
            @Override
            public void run() {
                swlPanel.setRefreshing(true);
                onRefresh();
            }
        });
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        swlPanel = ActivityCompat.requireViewById(this, R.id.swlPanel);
        swlPanel.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swlPanel.setOnRefreshListener(this);
        RecyclerView lstFotos = (RecyclerView) this.findViewById(R.id.lstFotos);
        mAdaptador = new FotosAdapter(new ArrayList<Foto>());
        lstFotos.setHasFixedSize(true);
        lstFotos.setAdapter(mAdaptador);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, getResources()
                .getInteger(R.integer.grid_columns),
                LinearLayoutManager.VERTICAL, false);
        lstFotos.setLayoutManager(gridLayoutManager);
        lstFotos.setItemAnimator(new DefaultItemAnimator());
        mEndlessScrollListener = new EndlessRecyclerOnScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                obtenerDatos();
            }
        };
        lstFotos.addOnScrollListener(mEndlessScrollListener);
    }

    // Obtiene los datos JSON de la lista de fotos de Instagram.
    private void obtenerDatos() {
        // Se crea el listener que recibirá la respuesta de la petición.
        Response.Listener<JSONObject> listenerRespuesta =
                new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject respuesta) {
                // Se crea la lista de datos parseando la respuesta.
                ArrayList<Foto> lista = procesarRespuesta(respuesta);
                // Se añade cada foto al adaotador.
                for (Foto foto : lista) {
                    mAdaptador.addItem(foto, mAdaptador.getItemCount());
                }
                // Se indica al gridview que ya ha finalizado la carga.
                swlPanel.setRefreshing(false);
            }

        };
        // Se crea la petición JSON.
        JsonObjectRequest peticion =
                new JsonObjectRequest(Method.GET,
                        mUrlSiguiente, null, listenerRespuesta, null);
        // Se añade la petición a la cola de peticiones.
        mColaPeticiones.add(peticion);
    }

    // Procesa el objeto JSON de respuesta, retornando la lista de fotos.
    private ArrayList<Foto> procesarRespuesta(JSONObject respuesta) {
        ArrayList<Foto> lista = new ArrayList<>();
        try {
            // Se procesa la respuesta para obtener los datos deseados.
            // Se obtiene cual debe ser la próxima petición para paginación.
            JSONObject paginationKeyJSONObject = respuesta
                    .getJSONObject(Instagram.PAGINACION_KEY);
            mUrlSiguiente = paginationKeyJSONObject
                    .getString(Instagram.SIGUIENTE_PETICION_KEY);
            Log.d("Mia", mUrlSiguiente);
            // Se obtiene el valor de la clave "data", que correponde al
            // array de datos.
            JSONArray dataKeyJSONArray = respuesta
                    .getJSONArray(Instagram.ARRAY_DATOS_KEY);
            // Por cada uno de los elementos del array de datos.
            for (int i = 0; i < dataKeyJSONArray.length(); i++) {
                // Se obtiene el elemento, que es un JSONObject.
                JSONObject elemento = dataKeyJSONArray.getJSONObject(i);
                // Si el tipo de elemento indica que es una imagen.
                if (elemento.getString(Instagram.TIPO_ELEMENTO_KEY).equals(
                        Instagram.TIPO_ELEMENTO_IMAGEN)) {
                    // Se crea un objeto modelo.
                    Foto foto = new Foto();
                    // Se obtiene el usuario.
                    JSONObject usuario = elemento
                            .getJSONObject(Instagram.USUARIO_KEY);
                    // Se obtiene del usuario el nombre de usuario y se
                    // guarda en el objeto modelo como descripción.
                    foto.setDescripcion(usuario
                            .getString(Instagram.NOMBRE_USUARIO_KEY));
                    // Se obtiene la imagen.
                    JSONObject imagen = elemento
                            .getJSONObject(Instagram.IMAGEN_KEY);
                    // Se obtiene la url de la miniatura de la imagen y se
                    // guarda en el objeto modelo.
                    foto.setUrl(imagen.getJSONObject(
                            Instagram.RESOLUCION_MINIATURA_KEY).getString(
                            Instagram.URL_KEY));
                    // Se añade el objeto modelo a la lista de datos
                    // para el mAdaptador.
                    lista.add(foto);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Se retorna la lista de fotos.
        return lista;
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
        mEndlessScrollListener.reset(0, true);
        // Se establece la url inicial.
        mUrlSiguiente = Instagram.getRecentMediaURL("algeciras");
        Log.d("Mia", mUrlSiguiente);
        // Si hay conexión a Internet se obtienen los datos.
        if (isConnectionAvailable()) {
            mAdaptador.removeAllItems();
            obtenerDatos();
        } else {
            mostrarToast(getString(R.string.no_hay_conexion_a_internet));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mEndlessScrollListener.reset(0, true);
    }

}
