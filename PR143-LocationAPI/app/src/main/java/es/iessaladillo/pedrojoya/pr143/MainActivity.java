package es.iessaladillo.pedrojoya.pr143;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import icepick.Icepick;
import icepick.State;


public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final long LOCATION_REQUEST_INTERVAL = 5000;
    private static final int PRC_LOCATION = 1;
    private static final int RC_CONFIGURACION = 2;
    private static final int RC_ACTUALIZAR_PLAY = 3;

    private TextView lblLocation;

    private GoogleApiClient mGoogleApiClient;
    @State
    public LocationRequest mLocationRequest;
    @State
    public Location mLastLocation;
    @State
    public boolean mLocalizacionIniciada;
    private BroadcastReceiver mReceptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se restaura el estado de la actividad (Icepick).
        Icepick.restoreInstanceState(this, savedInstanceState);
        initVistas();
        // Se crea el broadcast receiver.
        crearReceptor();
        // Se crea el cliente de acceso a la API.
        setupGoogleApiClient();
    }

    // Crea el receptor de broadcast.
    private void crearReceptor() {
        mReceptor = new BroadcastReceiver() {
            // Cuando se recibe el intent.
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean success = intent.getBooleanExtra(
                        GeocodingService.EXTRA_SUCCESS, false);
                String resultado = intent.getStringExtra(
                        GeocodingService.EXTRA_RESULTADO);
                if (success) {
                    // Si ha ido bien, se muestra la dirección.
                    lblLocation.append("\n" + resultado);
                } else {
                    Toast.makeText(MainActivity.this, resultado,
                            Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Se salva el estado de la actividad (Icepick).
        Icepick.saveInstanceState(this, outState);
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
    protected void onPause() {
        super.onPause();
        pararLocalizacion();
        // Se quita el registro del receptor.
        quitarReceptor();
    }

    // Para la actualización periódica de la localización.
    private void pararLocalizacion() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        mLocalizacionIniciada = false;
    }

    // Quita el registro del broadcast receiver.
    private void quitarReceptor() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceptor);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Se registra el receptor.
        registrarReceptor();
        // Si no está disponible GooglePlayServices se muestra el diálogo para
        // su instalación.
        int estado =
                GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (estado != ConnectionResult.SUCCESS) {
            Dialog dlg = GoogleApiAvailability.getInstance().getErrorDialog(this,
                    estado, RC_ACTUALIZAR_PLAY);
            dlg.show();
        }
        if (mGoogleApiClient.isConnected() && !mLocalizacionIniciada) {
            realizarSolicitud();
        }

    }

    // Registra el broadcast receiver.
    private void registrarReceptor() {
        // Se crea el filtro para al receptor.
        IntentFilter filtro = new IntentFilter(GeocodingService.ACCION);
        // Se registra el receptor.
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceptor, filtro);
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        lblLocation = (TextView) findViewById(R.id.lblLocation);
    }

    // Crea el cliente de acceso a la API.
    private void setupGoogleApiClient() {
        // Se quiere acceder a la api de localización.
        // La actividad actuará como listener cuando se conecte o cuando falle
        // la conexión.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    // Cuando ya estamos conectados con la API.
    @Override
    public void onConnected(Bundle bundle) {
        // Si no se tienen los permisos necesarios, se solicitan.
        if (!tienePermisos()) {
            // Se solicitan los permisos.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    PRC_LOCATION);
        } else {
            // Se inicia la localización.
            iniciarLocalizacion();
        }
    }


    // Comprueba los permisos necesarios.
    private boolean tienePermisos() {
        return ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED;
    }

    // Cuando se regresa del diálogo para conceder permisos.
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PRC_LOCATION) {
            if (!tienePermisos()) {
                // Si el usuario no quiere dar los permisos necesarios se
                // finaliza la app (mejor con un diálogo).
                Toast.makeText(this, R.string.permisos_requeridos,
                        Toast.LENGTH_SHORT).show();
                finish();
            } else {
                // Se inicia la localización.
                iniciarLocalizacion();
            }
        }
    }

    // Obtiene la última localización (incluyendo la dirección).
    private void obtenerUltimaPosicion() {
        mLastLocation =
                LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            // Se actualiza la vista.
            lblLocation.setText(
                    getString(R.string.coordenadas, mLastLocation.getLatitude(),
                            mLastLocation.getLongitude()));
            // Se busca la dirección correspondiente a la localización.
            buscarDireccion();
        }
    }

    // Busca la dirección correspondiente a la última localización.
    private void buscarDireccion() {
        if (Geocoder.isPresent()) {
            GeocodingService.start(this, mLastLocation);
        } else {
            Toast.makeText(MainActivity.this, R.string.sin_geocoding,
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Inicia la localización.
    private void iniciarLocalizacion() {
        configurarSolicitud();
        comprobarConfiguracion();
    }

    // Configura la solicitud de actualización periódica de la posición.
    private void configurarSolicitud() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(LOCATION_REQUEST_INTERVAL);
        mLocationRequest.setFastestInterval(LOCATION_REQUEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void comprobarConfiguracion() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        // Muestra el formulario de activación de GPS si es neceario.
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                        builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // La configuración es adecuada.
                        obtenerUltimaPosicion();
                        realizarSolicitud();
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // La configuración no es adecuada pero se puede activar
                        // con un diálogo.
                        try {
                            // Se muestra el diálogo para activar la configuración.
                            status.startResolutionForResult(
                                    MainActivity.this, RC_CONFIGURACION);
                        } catch (IntentSender.SendIntentException e) {
                            // Se ignora el error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Toast.makeText(MainActivity.this,
                                R.string.configuracion_inadecuada,
                                Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Realiza la solicitud de actualización periódica de posición.
    private void realizarSolicitud() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, MainActivity.this);
        mLocalizacionIniciada = true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_CONFIGURACION) {
            if (resultCode == RESULT_OK) {
                // Se ha configurado correctamente, por lo que se obtiene la
                // última posicion y se realiza la solicitud de actualización
                // periódica.
                obtenerUltimaPosicion();
                realizarSolicitud();
            }
            else {
                Toast.makeText(MainActivity.this,
                        R.string.sin_configuracion_adecuada,
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // Cuando se suspende la conexión.
    @Override
    public void onConnectionSuspended(int i) {
        Log.d(getString(R.string.app_name), "GoogleApiClient connection suspended");
        // Se vuelve a conectar.
        mGoogleApiClient.connect();
    }

    // Cuando cambia la localización.
    @Override
    public void onLocationChanged(Location location) {
        if (mLastLocation == null ||
                mLastLocation.getLatitude() != location.getLatitude() ||
                mLastLocation.getLongitude() != location.getLongitude()) {
            // Se copia la localización.
            mLastLocation = location;
            // Se escribe la localización actual.
            lblLocation.setText(getString(R.string.coordenadas,
                    mLastLocation.getLatitude(), mLastLocation.getLongitude()));
            // Se busca la dirección correspondiente a la localización.
            buscarDireccion();
            Log.d(getString(R.string.app_name), "Localización actualizada");
        }
    }

    // Cuando falla la conexión con la API.
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(getString(R.string.app_name), "GoogleApiClient connection failed: " +
                connectionResult.getErrorCode());
    }

}
