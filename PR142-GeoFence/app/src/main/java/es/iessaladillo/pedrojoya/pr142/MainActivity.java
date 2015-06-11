package es.iessaladillo.pedrojoya.pr142;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status> {

    private static final int RC_GEOFENCE_TRANSITIONS_INTENT_SERVICE = 0;

    private TextView lblActiviades;
    private SwitchCompat swActivar;

    private GoogleApiClient mGoogleApiClient;
    private DetectedActivitiesReceiver mDetectedActivitiesReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
        // Se crea el receptor.
        mDetectedActivitiesReceiver = new DetectedActivitiesReceiver();
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
    protected void onResume() {
        super.onResume();
        // Se registra el receptor.
        LocalBroadcastManager.getInstance(this).registerReceiver(mDetectedActivitiesReceiver,
                new IntentFilter(DetectedActivitiesIntentService.ACTION_DETECTED_ACTIVITIES));
    }

    @Override
    protected void onPause() {
        // Se quita el registro del receptor.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mDetectedActivitiesReceiver);
        super.onPause();
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
        lblActiviades = (TextView) findViewById(R.id.lblActividades);
        swActivar = (SwitchCompat) findViewById(R.id.swActivar);
        swActivar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
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
                .addApi(ActivityRecognition.API)
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

    // Retorna una solicitud personalizada de Geofence.
    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }

    // Retorna un PendingIntent para iniciar el servicio que recibe el intent
    // con la información Geofence.
    private PendingIntent getGeofenceServicePendingIntent() {
        // Intent explícito para llamar al intent service.
        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
        // Se retorna un Pending Intent para que se inicie dicho servicio. Si ya
        // existe una PendingIntent, se usa ese mismo (FLAG_UPDATE_CURRENT).
        return PendingIntent.getService(this,
                RC_GEOFENCE_TRANSITIONS_INTENT_SERVICE, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
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
            Log.e(getString(R.string.app_name), "Ha ido bien la activación / desactivación");
        }
        else {
            Log.e(getString(R.string.app_name), "Error al activar / desactivar");
        }
    }

    // Receptor de intent con la lista de actividades.
    private class DetectedActivitiesReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // Se obtiene el extra correspondiente a la lista de actividades.
            ArrayList<DetectedActivity> listaActividades =
                    intent.getParcelableArrayListExtra(

                            DetectedActivitiesIntentService.EXTRA_DETECTED_ACTIVITIES);
            // Se escribe en el TextView.
            String s ="";
            for (DetectedActivity actividad : listaActividades) {
                s = s + actividad.toString() + "\n";
            }
            lblActiviades.setText(s);
        }

    }
}
