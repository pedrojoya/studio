package pedrojoya.iessaladillo.es.pr107;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class PreferenciasActivity extends ActionBarActivity {

    // Al crear la actividad.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);
        // Se muestra el fragmento en la actividad.
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, new PreferenciasFragment())
                    .commit();
        }
    }

}
