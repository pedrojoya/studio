package es.iessaladillo.pedrojoya.pr142;

import android.content.Context;
import android.content.res.Resources;

import com.google.android.gms.location.GeofenceStatusCodes;

public class GeofenceErrorMessages {

    // Constructor privado para que no se pueda instanciar.
    private GeofenceErrorMessages() {}

    public static String getErrorString(Context context, int errorCode) {
        Resources resources = context.getResources();
        switch (errorCode) {
            case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
                return resources.getString(R.string.geofence_no_disponible);
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                return resources.getString(R.string.demasiados_geofences);
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                return resources.getString(R.string.demasiados_pending_intents);
            default:
                return resources.getString(R.string.error_desconocido_geofences);
        }
    }

}
