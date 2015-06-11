package es.iessaladillo.pedrojoya.pr142;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.ArrayList;

public class GeofenceTransitionsIntentService extends IntentService {

    public static final String ACTION_DETECTED_ACTIVITIES = "es.iessaladillo.pedrojoya.pr141.action.DETECTED_ACTIVITIES";

    public static final String EXTRA_DETECTED_ACTIVITIES = "es.iessaladillo.pedrojoya.pr141.extra.DETECTED_ACTIVITIES";

    public GeofenceTransitionsIntentService() {
        super("DetectedActivitiesIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            // Se extrae el resultado del intent con el que se ha llamado al
            // servicio, y de él la lista de actividades detectadas.
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            ArrayList<DetectedActivity> listaActividades = (ArrayList) result.getProbableActivities();
            // Se envía un broadcast local con la acción adecuada y se le añade
            // como extra la lista de actividades detectadas.
            Intent localIntent = new Intent(ACTION_DETECTED_ACTIVITIES);
            localIntent.putParcelableArrayListExtra(EXTRA_DETECTED_ACTIVITIES, listaActividades);
            LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
        }
    }

}
