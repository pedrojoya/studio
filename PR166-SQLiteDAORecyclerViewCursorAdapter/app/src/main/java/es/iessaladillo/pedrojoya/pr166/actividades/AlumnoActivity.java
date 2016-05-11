package es.iessaladillo.pedrojoya.pr166.actividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import es.iessaladillo.pedrojoya.pr166.R;
import es.iessaladillo.pedrojoya.pr166.fragmentos.AlumnoFragment;

public class AlumnoActivity extends AppCompatActivity {

    private static final String EXTRA_ID = "extra_id";
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
        FloatingActionButton btnGuardar = (FloatingActionButton) findViewById(R.id.btnGuardar);
        if (btnGuardar != null) {
            btnGuardar.setOnClickListener(new View.OnClickListener() {
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
    }

    // Carga el fragmento del alumno.
    private void cargarFragmento(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            AlumnoFragment frg;
            if (getIntent().hasExtra(EXTRA_ID)) {
                frg = AlumnoFragment.newInstance(getIntent().getLongExtra(EXTRA_ID, 0));
            } else {
                frg = AlumnoFragment.newInstance();
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContenido, frg, TAG_FRG_ALUMNO)
                    .commit();
        }
    }

    // Envía un intent para iniciar la actividad.
    public static void start(Activity actividad) {
        actividad.startActivity(new Intent(actividad, AlumnoActivity.class));
    }

    // Envía un intent para iniciar la actividad, recibiendo el id del alumno.
    public static void start(Activity actividad, long idAlumno) {
        Intent intent = new Intent(actividad, AlumnoActivity.class);
        intent.putExtra(EXTRA_ID, idAlumno);
        actividad.startActivity(intent);
    }

}
