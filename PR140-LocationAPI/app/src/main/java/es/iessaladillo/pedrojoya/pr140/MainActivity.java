package es.iessaladillo.pedrojoya.pr140;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final long LOCATION_REQUEST_INTERVAL = 1000;

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
        // Se obtiene la última localización.
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            lblLocation.setText(mLastLocation.toString());
        }
        // Se solicita la localización más exacta a la API cada segundo y la actividad
        // actuará como listener cuando cambie la localización.
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(LOCATION_REQUEST_INTERVAL);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,this);
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
        // Se escribe la localización actual.
        lblLocation.setText(location.toString());
    }

    // Cuando falla la conexión con la API.
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(getString(R.string.app_name), "GoogleApiClient connection failed: " +
                connectionResult.getErrorCode());
    }


}
