package es.iessaladillo.pedrojoya.pr143;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.os.ResultReceiver;
import android.text.TextUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GeocodingService extends IntentService {

    public static final int SUCCESS_RESULT = 0;
    public static final int FAILURE_RESULT = 1;

    public static final String EXTRA_RECEPTOR = "extra_receptor";
    public static final String EXTRA_RESULTADO = "extra_resultado";
    public static final String EXTRA_LOCALIZACION = "extra_localizacion";

    private static final int MAX_DIRECCIONES = 1;
    private ResultReceiver mReceptor;

    public GeocodingService() {
        super("GeocodingService");
    }

    public static void start(Context context, ResultReceiver receptorResultado, Location localizacion) {
        Intent intent = new Intent(context, GeocodingService.class);
        intent.putExtra(EXTRA_RECEPTOR, receptorResultado);
        intent.putExtra(EXTRA_LOCALIZACION, localizacion);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Si no se ha llamado correctamente al servicio, retornamos.
        if (intent == null || !intent.hasExtra(EXTRA_RECEPTOR) || !intent.hasExtra(EXTRA_LOCALIZACION))
            return;
        // Se inicializan las variables.
        String mensajeError = "";
        List<Address> direcciones = null;
        mReceptor = intent.getParcelableExtra(EXTRA_RECEPTOR);
        Location location = intent.getParcelableExtra(EXTRA_LOCALIZACION);
        // Se obtiene el Geocoder.
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        // Se obtiene la lista de direcciones correspondientes a la
        // localización, a través del geocoder.
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
            entregarRespuesta(FAILURE_RESULT, mensajeError);
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
            entregarRespuesta(SUCCESS_RESULT,
                    TextUtils.join(System.getProperty("line.separator"),
                            lineas));
        }
    }

    // Entrega la respuesta al cliente del servicio.
    private void entregarRespuesta(int resultCode, String result) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_RESULTADO, result);
        mReceptor.send(resultCode, bundle);
    }

}
