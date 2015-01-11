package pedrojoya.iessaladillo.es.pr107;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class DetalleActivity extends ActionBarActivity {

    // Constantes.
    public static final String EXTRA_DATOS = "datos";

    // Variables a nivel de clase.
    private String mDatos = "Sin datos";

    // Al crear la actividad.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        // Se obtienen los datos del intent con el que se ha sido llamado.
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_DATOS)) {
            mDatos = getIntent().getStringExtra(EXTRA_DATOS);
        }
        // Si no se viene de un estado anterior.
        if (savedInstanceState == null) {
            // Se carga el fragmento de detalle.
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frlContenedor, DetalleFragment.newInstance(mDatos))
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_detalle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}