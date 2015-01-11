package pedrojoya.iessaladillo.es.pr107;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity implements MeteoFragment.CallbackListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Si no provenimos de un cambio de orientación se carga
        // el fragmento principal.
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frlContenedor, MeteoFragment.newInstance("Madrid"))
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
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
            startActivity(new Intent(getApplicationContext(), PreferenciasActivity.class));
            return true;
        }

        if (id == R.id.mnuMapa) {
            mostrarLocalidadEnMapa();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void mostrarLocalidadEnMapa() {
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String localidad = preferencias.getString(getString(R.string.prefLocalidadKey), "Madrid");
        Uri geoLocalidad = Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q", localidad)
                .build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocalidad);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(String item) {
        Intent intent = new Intent(this, DetalleActivity.class);
        intent.putExtra(DetalleActivity.EXTRA_DATOS, item);
        startActivity(intent);
    }
}
