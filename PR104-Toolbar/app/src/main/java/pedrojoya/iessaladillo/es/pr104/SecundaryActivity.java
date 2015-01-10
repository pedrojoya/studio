package pedrojoya.iessaladillo.es.pr104;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class SecundaryActivity extends ActionBarActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secundary);
        // Se indica que la ActionBar va a corresponder a un widget Toobar.
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        // La ActionBar mostrará el icono de navegación.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Se infla la especificación de menú.
        getMenuInflater().inflate(R.menu.activity_secundary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Dependiendo del ítem pulsado.
        int id = item.getItemId();
        // Icono de navegación.
        if (id == android.R.id.home) {
            // Se navegacia hacia la actividad padre en la misma tarea.
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        // Configuración.
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
