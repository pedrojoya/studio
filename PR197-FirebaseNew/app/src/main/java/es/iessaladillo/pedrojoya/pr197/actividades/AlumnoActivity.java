package es.iessaladillo.pedrojoya.pr197.actividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import es.iessaladillo.pedrojoya.pr197.R;
import es.iessaladillo.pedrojoya.pr197.fragmentos.AlumnoFragment;

public class AlumnoActivity extends AppCompatActivity {

    private static final String EXTRA_KEY = "extra_key";
    private static final String TAG_FRG_ALUMNO = "tag_frag_alumno";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);
        setupToolbar();
        initVistas();
        cargarFragmento(savedInstanceState);
    }

    // Configura la toolbar.
    private void setupToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initVistas() {
        findViewById(R.id.btnGuardar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Se llama al método guardarAlumno del fragmento.
                AlumnoFragment frg = (AlumnoFragment) getSupportFragmentManager().findFragmentByTag(TAG_FRG_ALUMNO);
                if (frg != null) {
                    frg.guardarAlumno();
                }
            }
        });
    }

    // Carga el fragmento del alumno.
    private void cargarFragmento(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            AlumnoFragment frg;
            if (getIntent().hasExtra(EXTRA_KEY)) {
                frg = AlumnoFragment.newInstance(getIntent().getStringExtra(EXTRA_KEY));
            } else {
                frg = AlumnoFragment.newInstance();
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContenido, frg, TAG_FRG_ALUMNO)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Envía un intent para iniciar la actividad.
    @SuppressWarnings("SameParameterValue")
    public static void startForResult(Activity actividad, int requestCode) {
        actividad.startActivityForResult(new Intent(actividad, AlumnoActivity.class), requestCode);
    }

    // Envía un intent para iniciar la actividad, recibiendo la key del alumno.
    @SuppressWarnings("SameParameterValue")
    public static void startForResult(Activity actividad, String key, int requestCode) {
        Intent intent = new Intent(actividad, AlumnoActivity.class);
        intent.putExtra(EXTRA_KEY, key);
        actividad.startActivityForResult(intent, requestCode);
    }

}
