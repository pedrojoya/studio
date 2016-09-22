package es.iessaladillo.pedrojoya.pr172.saludo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import es.iessaladillo.pedrojoya.pr172.R;

public class SaludoActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_saludo);
        // Si no se proviene de cambio de configuración, se carga el fragmento.
        if (savedInstanceState == null) {
            cargarFragmento(SaludoFragment.newInstance());
        }
    }

    // Carga el fragmento en el FrameLayout de contenido.
    private void cargarFragmento(Fragment notesFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frlContenido, notesFragment);
        transaction.commit();
        Log.d("Mia", "Fragmento cargado");
    }

}