package pedrojoya.iessaladillo.es.pr176;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class AlumnoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);
        initVistas();
    }

    // Inicializa las vistas.
    private void initVistas() {
        configToolbar();
        configFab();
    }

    // Configura la Toolbar.
    private void configToolbar() {
        Toolbar toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    // Configura el FAB.
    private void configFab() {
        FloatingActionButton fabAgregar = ActivityCompat.requireViewById(this, R.id.fabAgregar);
        if (fabAgregar != null) {
            fabAgregar.setOnClickListener(v -> {
                DB.addAlumno(DB.getNextAlumno());
                setResult(RESULT_OK);
                finish();
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Cuando se pulsa la flecha de navegación de la toolbar se simular como
        // si se hubiera pulsado Atrás.
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Para iniciar la actividad esperando respuesta.
    @SuppressWarnings("SameParameterValue")
    public static void startForResult(Activity activity, int requestCode) {
        activity.startActivityForResult(new Intent(activity, AlumnoActivity.class), requestCode);
    }

}
