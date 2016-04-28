package es.iessaladillo.pedrojoya.pr083;

import android.content.Context;
import android.support.v4.content.Loader;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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

// Cargador que entrega una lista de alumnos, obteniéndolas
// en un hilo secundario a partir de petición HTTP con respuesta JSON.
@SuppressWarnings("unused")
class AlumnosLoader extends
        Loader<ArrayList<Alumno>> {

    private static final String URL_DATOS =
            "https://dl.dropboxusercontent.com/u/67422/Android/json/datos.json";
    private final RequestQueue mColaPeticiones;

    private ArrayList<Alumno> mDatos;

    public AlumnosLoader(Context context) {
        super(context);
        // Se obtiene la cola de peticiones de Volley.
        mColaPeticiones = App.getRequestQueue();

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

    @Override
    protected void onForceLoad() {
        // realizarPeticionJSON();
        realizarPeticionGson();
    }

    // Cuando se deben entregar los datos. Recibe los datos a entregar.
    @Override
    public void deliverResult(ArrayList<Alumno> datos) {
        // Cacheamos los datos por si nos los vuelven a pedir.
        mDatos = datos;
        // Se entregan los datos.
        super.deliverResult(datos);
    }

    // Añade a la cola de peticiones una petición JSON.
    private void realizarPeticionJSON() {
        // Se crea el listener para la respuesta.
        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                // Se procesa la cadena JSON, obteniendo el ArrayList de
                // alumnos.
                ArrayList<Alumno> alumnos = procesarJSON(response);
                // Se entregan los resultados.
                deliverResult(alumnos);
            }

        };
        // Se crea el listener de error.
        Response.ErrorListener errorListener = new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Se entrega como resultado una lista vacía.
                deliverResult(new ArrayList<Alumno>());
            }

        };
        // Se crea la petición.
        JsonArrayRequest peticion = new JsonArrayRequest(URL_DATOS, listener,
                errorListener);
        // Se ñade la petición a la cola de Volley.
        mColaPeticiones.add(peticion);
    }

    // Añade a la cola de peticiones una petición Gson.
    private void realizarPeticionGson() {
        // Se crea el listener para la respuesta.
        Response.Listener<ArrayList<Alumno>> listener = new Response.Listener<ArrayList<Alumno>>() {

            @Override
            public void onResponse(ArrayList<Alumno> response) {
                // Se entregan los resultados.
                deliverResult(response);
            }

        };
        // Se crea el listener de error.
        Response.ErrorListener errorListener = new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Se entrega como resultado una lista vacía.
                deliverResult(new ArrayList<Alumno>());
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
        mColaPeticiones.add(peticion);
    }

    // Procesa la cadena JSON y retorna el ArrayList de alumnos.
    private ArrayList<Alumno> procesarJSON(JSONArray result) {
        // Se crea el ArrayList a retornar.
        ArrayList<Alumno> alumnos = new ArrayList<>();
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
    private ArrayList<Alumno> procesarGSON(String result) {
        // Se crea el objeto Gson.
        Gson gson = new Gson();
        Type tipoListaAlumnos = new TypeToken<List<Alumno>>() {
        }.getType();
        // Se procesa la cadena JSON y se retorna.
        return gson.fromJson(result, tipoListaAlumnos);
    }

}