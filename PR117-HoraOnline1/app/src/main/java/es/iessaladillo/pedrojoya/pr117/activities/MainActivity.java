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
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import es.iessaladillo.pedrojoya.pr117.R;
import es.iessaladillo.pedrojoya.pr117.alarms.UTCTimeUpdateNeededAlarm;
import es.iessaladillo.pedrojoya.pr117.data.utctime.UtctimeColumns;
import es.iessaladillo.pedrojoya.pr117.services.ApiService;


public class MainActivity extends ActionBarActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_ID = 1;
    private static final int PRIORIDAD_SUPERIOR = 2;

    private TextView mLblDatetime;

    private LocalBroadcastManager mLocalBroadcastManager;
    private BroadcastReceiver mUTCTimeUpdatedReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLoaderManager().initLoader(LOADER_ID, null, this);
        initVistas();
        configReceiver();
        UTCTimeUpdateNeededAlarm.set(getApplicationContext(), UTCTimeUpdateNeededAlarm.ALARM_INTERVAL);
    }

    // Configura el receptor de mensajes.
    private void configReceiver() {
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
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

    private void initVistas() {
        mLblDatetime = (TextView) findViewById(R.id.lblDatetime);
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

    public void refrescar() {
        Intent intent = new Intent(getApplicationContext(), ApiService.class);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(
                ApiService.ACTION_UTCTIME_UPDATED);
//        mLocalBroadcastManager.registerReceiver(mUTCTimeUpdatedReceiver, filter);
        filter.setPriority(PRIORIDAD_SUPERIOR);
        registerReceiver(mUTCTimeUpdatedReceiver, filter);
    }

    @Override
    protected void onPause() {
//        mLocalBroadcastManager.unregisterReceiver(mUTCTimeUpdatedReceiver);
        unregisterReceiver(mUTCTimeUpdatedReceiver);
        super.onPause();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                UtctimeColumns.CONTENT_URI, UtctimeColumns.ALL_COLUMNS,
                null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null) {
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                mLblDatetime.setText(cursor.getString(
                        cursor.getColumnIndexOrThrow(UtctimeColumns.DATETIME)));
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

}
