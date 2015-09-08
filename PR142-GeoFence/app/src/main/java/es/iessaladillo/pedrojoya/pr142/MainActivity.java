package es.iessaladillo.pedrojoya.pr142;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status> {

    private static final int RC_GEOFENCE_TRANSITIONS_INTENT_SERVICE = 0;

    private GoogleApiClient mGoogleApiClient;
    private ArrayList<Geofence> mGeofencesList;

    private SwitchCompat swActivar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGeofencesList = new ArrayList<Geofence>();
        populateGeofencesList();
        initVistas();
        // Se crea el cliente de acceso a la API.
        setupGoogleApiClient();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Se realiza la conexión con la API.
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        // Se realiza la desconexión de la API.
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        swActivar = (SwitchCompat) findViewById(R.id.swActivar);
        swActivar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isActive) {
                if (isActive) {
                    activarReconocimiento();
                } else {
                    desactivarReconocimiento();
                }
            }
        });
    }

    // Crea el cliente de acceso a la API.
    private synchronized void setupGoogleApiClient() {
        // Se quiere acceder a la api de localización.
        // La actividad actuará como listener cuando se conecte o cuando falle
        // la conexión.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    private void activarReconocimiento() {
        if (!mGoogleApiClient.isConnected()) {
            Toast.makeText(this, R.string.cliente_api_no_conectado,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        // Se realiza una solicitud personalizada de detección de Geofence. La
        // actividad actuará como listener cuando se haya activado.
        // Cuando se produzca una transición en el Geofence el cliente de la API
        // creará un intent que enviará al servicio especificado al crear el
        // PendingIntent.
        LocationServices.GeofencingApi.addGeofences(
                mGoogleApiClient,
                getGeofencingRequest(),
                getGeofenceServicePendingIntent()
        ).setResultCallback(this);
    }

    private void desactivarReconocimiento() {
        if (!mGoogleApiClient.isConnected()) {
            Toast.makeText(this, R.string.cliente_api_no_conectado, Toast.LENGTH_SHORT).show();
            return;
        }
        // Se desactiva el monitoreo de Geofence. La actividad actuará
        // como listener cuando se haya desactivado.
        LocationServices.GeofencingApi.removeGeofences(
                mGoogleApiClient,
                // This is the same pending intent that was used in addGeofences().
                getGeofenceServicePendingIntent()
        ).setResultCallback(this);    }

    // Cuando ya estamos conectados con la API.
    @Override
    public void onConnected(Bundle bundle) {
    }

    // Cuando se suspende la conexión.
    @Override
    public void onConnectionSuspended(int i) {
        Log.d(getString(R.string.app_name), "GoogleApiClient connection suspended");
        swActivar.setChecked(false);
        // Se vuelve a conectar.
        mGoogleApiClient.connect();
    }

    // Cuando falla la conexión con la API.
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(getString(R.string.app_name), "GoogleApiClient connection failed: " +
                connectionResult.getErrorCode());
        swActivar.setChecked(false);
    }

    // Cuando se recibe el resultado del cliente de la API.
    @Override
    public void onResult(Status status) {
        if (status.isSuccess()) {
            Toast.makeText(this, "Activación correcta", Toast.LENGTH_SHORT).show();
        }
        else {
            String errorMessage = GeofenceErrorMessages.getErrorString(this, status.getStatusCode());
            Log.e(getString(R.string.app_name), errorMessage);
        }
    }

    // Rellena la lista de geofences
    private void populateGeofencesList() {
        // Se crea un Geofence por cada lugar y se añade a la lista de geofences.
        Geofence.Builder builder;
        for (HashMap.Entry<String, LatLng> lugar : Constants.LUGARES.entrySet()) {
            builder = new Geofence.Builder();
            // El RequestId corresponde al nombre asignado al lugar.
            builder.setRequestId(lugar.getKey());
            // Se trata de una región circular.
            builder.setCircularRegion(
                    lugar.getValue().latitude,
                    lugar.getValue().longitude,
                    Constants.GEOFENCE_RADIUS_IN_METERS
            );
            builder.setExpirationDuration(Constants.GEOFENCE_EXPIRATION_IN_MILLISECONDS);
            // Se dispararán los eventos de entrar y de salir de una Geofence.
            builder.setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT);
            mGeofencesList.add(builder.build());
        }
    }

    // Crea la petición de detección de eventos sobre Geofences.
    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        // Se disparará el evento de entrada si ya nos encontramos en una Geofence.
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        // Añadimos a la solicitud la lista de Geofences.
        builder.addGeofences(mGeofencesList);
        return builder.build();
    }

    // Retorna un PendingIntent para iniciar el servicio que recibe el intent
    // con la información Geofence.
    private PendingIntent getGeofenceServicePendingIntent() {
        // Intent explícito para llamar al intent service.
        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
        // Se retorna un Pending Intent para que se inicie dicho servicio. Si ya
        // existe un PendingIntent, se usa ese mismo (FLAG_UPDATE_CURRENT).
        return PendingIntent.getService(this,
                RC_GEOFENCE_TRANSITIONS_INTENT_SERVICE, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
