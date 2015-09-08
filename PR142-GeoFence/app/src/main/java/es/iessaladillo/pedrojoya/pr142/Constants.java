package es.iessaladillo.pedrojoya.pr142;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

/**
 * Created by pedro on 28/6/15.
 */
public final class Constants {

    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = 12 * 60 * 60 * 1000;

    private Constants() {}

    public static final float GEOFENCE_RADIUS_IN_METERS = 500;
    public static final HashMap<String, LatLng> LUGARES = new HashMap<>();

    static {
        LUGARES.put("Casa", new LatLng(36.106152, -5.442647));
    }
}
