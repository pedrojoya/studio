package es.iessaladillo.pedrojoya.pr152.actividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import es.iessaladillo.pedrojoya.pr152.R;
import es.iessaladillo.pedrojoya.pr152.fragmentos.PreferenciasFragment;

public class PreferenciasActivity extends AppCompatActivity implements PreferenceFragmentCompat
        .OnPreferenceStartScreenCallback {

    private static final String EXTRA_KEY = "extra_key";
    private String mPreferenceScreenKey;

    public static void start(Activity actividad, String preferenceScreenKey) {
        Intent intent = new Intent(actividad, PreferenciasActivity.class);
        intent.putExtra(EXTRA_KEY, preferenceScreenKey);
        actividad.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (getIntent() != null && getIntent().hasExtra(EXTRA_KEY)) {
            mPreferenceScreenKey = getIntent().getStringExtra(EXTRA_KEY);
        }
        // Se muestra el fragmento en la actividad.
        getSupportFragmentManager().beginTransaction().replace(R.id.flContenido,
                PreferenciasFragment.newInstance(mPreferenceScreenKey)).commit();
    }

    @Override
    public boolean onPreferenceStartScreen(PreferenceFragmentCompat preferenceFragmentCompat,
            PreferenceScreen preferenceScreen) {
        PreferenciasActivity.start(this, preferenceScreen.getKey());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
