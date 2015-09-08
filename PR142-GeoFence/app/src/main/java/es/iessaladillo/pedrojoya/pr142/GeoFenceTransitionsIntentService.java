package es.iessaladillo.pedrojoya.pr142;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.List;

public class GeofenceTransitionsIntentService extends IntentService {

    private static final int NC_EVENTO = 1;

    public GeofenceTransitionsIntentService() {
        super("GeofenceTransitionsIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Se extrae del intent recibido el evento Geofence que se ha producido.
        GeofencingEvent evento = GeofencingEvent.fromIntent(intent);
        // Si el evento es de error, se informa y se retorna.
        if (evento.hasError()) {
            Log.e(getString(R.string.app_name), GeofenceErrorMessages.getErrorString(
                    getApplicationContext(), evento.getErrorCode()));
            return;
        }
        // Si el tipo de transición que ha provocado el evento es la entrada a
        // un Geofence o la salida de un Geofence.
        int transicion = evento.getGeofenceTransition();
        if (transicion == Geofence.GEOFENCE_TRANSITION_ENTER ||
                transicion == Geofence.GEOFENCE_TRANSITION_EXIT) {
            // Se obtiene la lista de Geofences de generaron el evento.
            List<Geofence> listaGeofences = evento.getTriggeringGeofences();
            // Se muestra al usuario una notificación con el detalle del evento.
            String detalle = getGeofenceTransitionDetails(this, transicion, listaGeofences);
            enviarNotificacion(detalle);
            Log.i(getString(R.string.app_name), detalle);
        }
        else {
            Log.e(getString(R.string.app_name), "Tipo de evento no válido");
        }
    }

    // Retorna una cadena informativa con el detalle del evento, incluyendo
    // el tipo de transición y los resquestId de los geofences que lo han
    // generado.
    private String getGeofenceTransitionDetails(
            GeofenceTransitionsIntentService geofenceTransitionsIntentService,
            int transicion, List<Geofence> listaGeofences) {
        StringBuilder detalle = new StringBuilder();
        detalle.append(transicion == Geofence.GEOFENCE_TRANSITION_ENTER ?
                "Ha entrado en: " : "Ha salido de: ");
        // Se obtiene la lista de requestId correspondientes a las geofences
        // que han generado el evento.
        ArrayList<String> listaRequestIds = new ArrayList<>();
        for (Geofence geo : listaGeofences) {
            listaRequestIds.add(geo.getRequestId());
        }
        // Se añaden a la cadena resultante separadas por coma.
        detalle.append(TextUtils.join(",", listaRequestIds));
        return detalle.toString();
    }

    // Envía una notificación con la cadena recibida.
    private void enviarNotificacion(String detalle) {
        NotificationManager gestor =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder b = new NotificationCompat.Builder(this);
        b.setLargeIcon(((BitmapDrawable) getResources().getDrawable(R.mipmap.ic_launcher))
                .getBitmap());
        b.setContentTitle(getString(R.string.app_name));
        b.setContentText(detalle);
        b.setTicker(getString(R.string.app_name));
        gestor.notify(NC_EVENTO, b.build());
    }

}
