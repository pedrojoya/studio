package es.iessaladillo.pedrojoya.pr117.activities;

import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import es.iessaladillo.pedrojoya.pr117.R;
import es.iessaladillo.pedrojoya.pr117.data.utctime.UtctimeColumns;
import es.iessaladillo.pedrojoya.pr117.services.ApiService;
import es.iessaladillo.pedrojoya.pr117.sync.UTCTimeUpdateNeededAlarm;

public class MainActivity extends ActionBarActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_ID = 1;
    private static final int PRIORIDAD_SUPERIOR = 2;

    private TextView mLblDatetime;

    private BroadcastReceiver mUTCTimeUpdatedReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se inicializan las vistas.
        initVistas();
        // Se inicializa al cargador de datos.
        getLoaderManager().initLoader(LOADER_ID, null, this);
        // Se configura el receptor de mensajes desde el servicio de la API.
        configReceiver();
        // Se establece la alarma de actualización de datos.
        UTCTimeUpdateNeededAlarm.set(getApplicationContext(), UTCTimeUpdateNeededAlarm.ALARM_INTERVAL);
    }

    // Obtiene e inicializa las vista.s
    private void initVistas() {
        mLblDatetime = (TextView) findViewById(R.id.lblDatetime);
    }

    // Configura el receptor de mensajes desde el servicio de la API.
    private void configReceiver() {
        mUTCTimeUpdatedReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(getApplicationContext(),
                        "Hora actualizada",
                        Toast.LENGTH_SHORT).show();
                abortBroadcast();
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuRefrescar) {
            refrescar();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Inicia el servicio de actualización de datos desde la API.
    public void refrescar() {
        Intent intent = new Intent(getApplicationContext(), ApiService.class);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Se registra el receptor de mensajes desde el servicio de la API con una
        // prioridad alta.
        IntentFilter filter = new IntentFilter(
                ApiService.ACTION_UTCTIME_UPDATED);
        filter.setPriority(PRIORIDAD_SUPERIOR);
        registerReceiver(mUTCTimeUpdatedReceiver, filter);
    }

    @Override
    protected void onPause() {
        // Se quita del registro el receptor de mensajes desde el servicio de la API.
        unregisterReceiver(mUTCTimeUpdatedReceiver);
        super.onPause();
    }

    // Cuando se debe crear el cargador de datos.
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Se crea y retorna un CursorLoader que carga todos los datos de la tabla Utctime
        // a través del Content Provider.
        return new CursorLoader(this,
                UtctimeColumns.CONTENT_URI, UtctimeColumns.ALL_COLUMNS,
                null, null, null);
    }

    // Cuando se terminan de cargar los datos del cargador.
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Se recorre el cursor y se escriben los datos.
        if (cursor != null) {
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                mLblDatetime.setText(cursor.getString(
                        cursor.getColumnIndexOrThrow(UtctimeColumns.DATETIME)));
            }
        }
    }

    // Cuando se resetea el cargador de datos.
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

}
