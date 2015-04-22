package es.iessaladillo.pedrojoya.pr050;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class PreferenciasActivity extends AppCompatActivity {

    // Al crear la actividad.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);
        // Se muestra el icono de navegación junto al icono de la aplicación.
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
    }

    // Al pulsar sobre un ítem del menú se opciones.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Dependiendo del ítem seleccionado.
        switch (item.getItemId()) {
            case android.R.id.home: // Icono de navegación.
                // Se muestra la actividad principal de la aplicación.
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            default:
                // Se propaga el evento porque no ha sido resuelto.
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

}
