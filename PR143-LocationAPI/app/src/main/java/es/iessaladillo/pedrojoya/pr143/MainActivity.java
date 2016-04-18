package es.iessaladillo.pedrojoya.pr143;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final long LOCATION_REQUEST_INTERVAL = 10000;
    private static final int PRC_LOCATION = 1;

    private TextView lblLocation;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        if (!iniciarLocalizacion()) {
            // Se solicitan los permisos.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    PRC_LOCATION);
        }
    }

    // Cuando se regresa del diálogo para conceder permisos.
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PRC_LOCATION) {
            if (!iniciarLocalizacion()) {
                Toast.makeText(this, R.string.permisos_requeridos, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    // Obtiene la última localización conocida y activa la actualización de la
    // localización. La actividad actuará como listener.
    // Retorna si se ha inicializado la localización.
    public boolean iniciarLocalizacion() {
        // Si se tienen los permisos necesarios.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            // Se obtiene la última localización.
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                lblLocation.setText(mLastLocation.getLongitude() + " / " + mLastLocation.getLatitude());
                // Se busca la dirección correspondiente a la localización.
                if (Geocoder.isPresent()) {
                    GeocodingService.start(this, new DireccionResultReceiver(new Handler()), mLastLocation);
                }
                else {
                    Toast.makeText(MainActivity.this, "No ha servicio de Geolocalización inversa", Toast.LENGTH_SHORT).show();
                }
            }
            // Se solicita que se notifique a la actividad cuando cambie
            // la localización.
            mLocationRequest = LocationRequest.create();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setInterval(LOCATION_REQUEST_INTERVAL);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            return true;
        }
        return false;
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
        if (mLastLocation.getLatitude() != location.getLatitude() ||
                mLastLocation.getLongitude() != location.getLongitude()) {
            // Se copia la localización.
            mLastLocation = location;
            // Se escribe la localización actual.
            lblLocation.setText(mLastLocation.getLongitude() + " / " + mLastLocation.getLatitude());
            // Se busca la dirección correspondiente a la localización.
            if (Geocoder.isPresent()) {
                GeocodingService.start(this, new DireccionResultReceiver(new Handler()), mLastLocation);
            } else {
                Toast.makeText(MainActivity.this, "No ha servicio de Geolocalización inversa", Toast.LENGTH_SHORT).show();
            }
        }
        Log.d(getString(R.string.app_name), "Localización actualizada");
    }

    // Cuando falla la conexión con la API.
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(getString(R.string.app_name), "GoogleApiClient connection failed: " +
                connectionResult.getErrorCode());
    }

    @SuppressLint("ParcelCreator")
    class DireccionResultReceiver extends ResultReceiver {

        public DireccionResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            // Display the address string
            // or an error message sent from the intent service.
            String direccion = resultData.getString(GeocodingService.EXTRA_RESULTADO);
            if (resultCode == GeocodingService.SUCCESS_RESULT) {
                lblLocation.append(" " + direccion);
            }
        }

    }


}
