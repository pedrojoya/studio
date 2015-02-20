package es.iessaladillo.pedrojoya.pr117.services;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import es.iessaladillo.pedrojoya.pr117.data.utctime.UtctimeColumns;
import es.iessaladillo.pedrojoya.pr117.data.utctime.UtctimeContentValues;
import es.iessaladillo.pedrojoya.pr117.model.UTCTime;
import es.iessaladillo.pedrojoya.pr117.volley.GsonObjectRequest;
import es.iessaladillo.pedrojoya.pr117.volley.VolleyInstance;

// Servicio de actualización desde la API.
public class ApiService extends IntentService {

    private static final String SERVICE_NAME = "HoraOnline";
    private static final String URL_DATOS = "http://json-time.appspot.com/time.json";
    public static final String ACTION_UTCTIME_UPDATED = "es.iessaladillo.pedrojoya.pr117.action.utctime_updated";

    private RequestQueue colaPeticiones;

    public ApiService() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Se obtiene la cola de peticiones.
        colaPeticiones = VolleyInstance.getInstance(getApplicationContext()).getRequestQueue();
        // Se realiza la petición.
        realizarPeticionGson();
    }

    // Añade a la cola de peticiones una petición Gson.
    private void realizarPeticionGson() {
        // Se crea el listener para la respuesta.
        Response.Listener<UTCTime> listener = new Response.Listener<UTCTime>() {

            @Override
            public void onResponse(UTCTime response) {
                guardarEnDB(response);
                enviarBroadcast();
            }
        };
        // Se crea el listener de error.
        Response.ErrorListener errorListener = new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(SERVICE_NAME, error.toString());
            }
        };
        // Se crea la petición.
        Gson gson = new Gson();
        Type tipo = new TypeToken<UTCTime>() {}.getType();
        GsonObjectRequest<UTCTime> peticion = new GsonObjectRequest<UTCTime>(
                Request.Method.GET, URL_DATOS, tipo, listener, errorListener,
                gson);
        // Se añade la petición a la cola de Volley.
        colaPeticiones.add(peticion);
    }

    // Guarda en la BD el UTCTime recibido.
    private void guardarEnDB(UTCTime response) {
        ContentResolver cr = getContentResolver();
        cr.delete(UtctimeColumns.CONTENT_URI,null,null);
        UtctimeContentValues cv = new UtctimeContentValues();
        cr.insert(UtctimeColumns.CONTENT_URI, new UtctimeContentValues(response).values());
    }

    // Envía un mensaje a quién corresponda de que los datos han sido actualizados.
    private void enviarBroadcast() {
        Intent intent = new Intent(ACTION_UTCTIME_UPDATED);
        // Envía un broadcast ordenado, de manera que si la actiividad está en primer plano,
        // su receptor registrado dinámico y que tiene más prioridad que el registrado estáticamente
        // reciba primero el intent y pueda si lo desea abortar el broadcast para que el intent
        // no sea recibido por el resto de receptores.
        sendOrderedBroadcast(intent, null);
    }

}
