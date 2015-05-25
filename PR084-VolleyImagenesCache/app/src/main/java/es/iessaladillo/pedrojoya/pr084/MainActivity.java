package es.iessaladillo.pedrojoya.pr084;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EndlessGridView
        .LoadAgent {

    // Vistas.
    private EndlessGridView lstFotos;

    // Variables.
    private MenuItem mnuActualizar;
    private FotosAdapter adaptador;
    private RequestQueue colaPeticiones;
    private String sUrlSiguiente;

    // Al crearse la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstFotos = (EndlessGridView) this.findViewById(R.id.lstFotos);
        // Se establece la actividad como listener del scroll del gridview.
        lstFotos.setLoadAgent(this);
        // Se obtiene la cola de peticiones de Volley.
        colaPeticiones = App.getRequestQueue();
        // Se crea y asigna el adaptador para el ListView.
        ArrayList<Foto> fotos = new ArrayList<>();
        adaptador = new FotosAdapter(this, fotos);
        lstFotos.setAdapter(adaptador);
        // Se establece la url inicial.
        sUrlSiguiente = Instagram.getRecentMediaURL("algeciras");
        // Si hay conexión a Internet se obtienen los datos.
        if (isConnectionAvailable()) {
            obtenerDatos();
        } else {
            mostrarToast(getString(R.string.no_hay_conexion_a_internet));
        }
    }

    // Obtiene los datos JSON de la lista de fotos de Instagram.
    private void obtenerDatos() {
        // Se muestra el círculo de progreso.
        if (mnuActualizar != null) {
            mnuActualizar.setVisible(true);
        }
        // Se crea el listener que recibirá la respuesta de la petición.
        Response.Listener<JSONObject> listenerRespuesta =
                new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject respuesta) {
                // Se crea la lista de datos parseando la respuesta.
                ArrayList<Foto> lista = procesarRespuesta(respuesta);
                // Se añaden las fotos de la lista al adaptador.
                agregarAlAdaptador(lista);
                // Se oculta el progreso.
                if (mnuActualizar != null) {
                    mnuActualizar.setVisible(false);
                }
                // Se indica al gridview que ya ha finalizado la carga.
                lstFotos.setLoaded();
            }

            // Agraga al adaptador la lista de fotos obtenidas.
            private void agregarAlAdaptador(ArrayList<Foto> lista) {
                // Se añade cada foto al adaotador.
                for (Foto foto : lista) {
                    adaptador.add(foto);
                }
                // Se notifican los cambios en los datos.
                adaptador.notifyDataSetChanged();
            }

        };
        // Se crea la petición JSON.
        JsonObjectRequest peticion =
                new JsonObjectRequest(Method.GET,
                        sUrlSiguiente, listenerRespuesta, null);
        // Se añade la petición a la cola de peticiones.
        colaPeticiones.add(peticion);
    }

    // Procesa el objeto JSON de respuesta, retornando la lista de fotos.
    private ArrayList<Foto> procesarRespuesta(JSONObject respuesta) {
        ArrayList<Foto> lista = new ArrayList<>();
        try {
            // Se procesa la respuesta para obtener los datos deseados.
            // Se obtiene cual debe ser la próxima petición para paginación.
            JSONObject paginationKeyJSONObject = respuesta
                    .getJSONObject(Instagram.PAGINACION_KEY);
            sUrlSiguiente = paginationKeyJSONObject
                    .getString(Instagram.SIGUIENTE_PETICION_KEY);
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
                    // para el adaptador.
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

    // Cuando el gridview solicta más datos.
    @Override
    public void loadData() {
        // Se obtienen más fotos.
        obtenerDatos();
    }

}
