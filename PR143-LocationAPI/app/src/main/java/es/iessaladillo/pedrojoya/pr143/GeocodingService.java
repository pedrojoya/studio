package es.iessaladillo.pedrojoya.pr143;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GeocodingService extends IntentService {

    public static final String ACCION = "es.iessaladillo.pedrojoya.pr143.action.geocoding";
    public static final String EXTRA_SUCCESS = "extra_success";
    public static final String EXTRA_RESULTADO = "extra_resultado";

    private static final String EXTRA_LOCALIZACION = "extra_localizacion";
    private static final int MAX_DIRECCIONES = 1;

    public GeocodingService() {
        super("GeocodingService");
    }

    // Inicia el servicio. Recibe el contexto y la localización a buscar.
    public static void start(Context context, Location localizacion) {
        Intent intent = new Intent(context, GeocodingService.class);
        intent.putExtra(EXTRA_LOCALIZACION, localizacion);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Si no se ha llamado correctamente al servicio, retornamos.
        if (intent == null || !intent.hasExtra(EXTRA_LOCALIZACION)) {
            entregarRespuesta(false, getString(R.string.localizacion_requerida));
            return;
        }
        // Si el servicio de Geocoding no está disponible, retornamos.
        if (!Geocoder.isPresent()) {
            entregarRespuesta(false, getString(R.string.geocoder_no_disponible));
            return;
        }
        // Se inicializan las variables.
        String mensajeError = "";
        List<Address> direcciones = null;
        Location location = intent.getParcelableExtra(EXTRA_LOCALIZACION);
        // Se obtiene el Geocoder (para el idioma del dispositivo).
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        // Se obtiene la lista de direcciones correspondientes a la
        // localización, a través del geocoder (máximo 1 dirección)
        try {
            direcciones = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    MAX_DIRECCIONES);
        } catch (IOException e) {
            // Si se produce un error es que el servicio de Geocoder no
            // está disponible.
            mensajeError = getString(R.string.geocoder_no_disponible);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Si la longitud y latitud indicadas no son válidas.
            mensajeError = getString(R.string.lat_long_invalidas);
        }
        // Si no se ha encontrado ninguna dirección.
        if (direcciones == null || direcciones.size() == 0) {
            // Si no se ha producido un error anterior.
            if (mensajeError.isEmpty()) {
                mensajeError = getString(R.string.direccion_no_encontrada);
            }
            // Se entrega el resultado de error al cliente del servicio.
            entregarRespuesta(false, mensajeError);
        } else {
            // Si se ha obtenido una dirección correctamente, se coge cada
            // una de sus líneas.
            Address direccion = direcciones.get(0);
            ArrayList<String> lineas = new ArrayList<>();
            for (int i = 0; i < direccion.getMaxAddressLineIndex(); i++) {
                lineas.add(direccion.getAddressLine(i));
            }
            // Se entrega la dirección resultante al cliente del servicio,
            // en forma de cadena.
            entregarRespuesta(true,
                    TextUtils.join(System.getProperty("line.separator"),
                            lineas));
        }
    }

    // Entrega la respuesta al cliente del servicio.
    private void entregarRespuesta(boolean success, String result) {
        // Se envía el broadcast.
        Intent intent = new Intent(ACCION);
        intent.putExtra(EXTRA_SUCCESS, success);
        intent.putExtra(EXTRA_RESULTADO, result);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}
