package es.iessaladillo.pedrojoya.pr050;

import java.lang.reflect.Field;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;

public class MainActivity extends ActionBarActivity implements FotoFragment.Listener,
        InfoFragment.Listener {

    // Constantes.
    private static final String TAG_FOTO_FRAGMENT = "fotoFragment";
    private static final String TAG_INFO_FRAGMENT = "infoFragment";

    // Cuando se crea la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se activa el ítem de overflow en dispositivos con botón físico de
        // menú.
        overflowEnDispositivoConTeclaMenu();
        // Se carga el fragmento con la foto (sólo si no está ya).
        FotoFragment frg = (FotoFragment) getFragmentManager()
                .findFragmentByTag(TAG_FOTO_FRAGMENT);
        if (frg == null) {
            frg = FotoFragment.newInstance(R.drawable.bench);
            getFragmentManager().beginTransaction()
                    .replace(R.id.frmFragmento, frg, TAG_FOTO_FRAGMENT)
                    .commit();
        }
    }

    // Al crearse el menú de opciones.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Se infla el menú a partir del XML.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        // Se retorna lo que devuelva la actividad.
        return super.onCreateOptionsMenu(menu);
    }

    // Cuando se pulsa un ítem del menú de opciones.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Dependiendo del item pulsado se realiza la acción deseada.
        switch (item.getItemId()) {
        case R.id.mnuPreferencias:
            mostrarPreferencias();
            break;
        default:
            // Se propaga el evento porque no ha sido resuelto.
            return super.onOptionsItemSelected(item);
        }
        // Retorna que el evento ya ha sido gestionado.
        return true;
    }

    // Muestra la actividad de preferencias.
    private void mostrarPreferencias() {
        Intent intent = new Intent(this, PreferenciasActivity.class);
        startActivity(intent);
    }

    // Cuando se solicita la Info.
    @Override
    public void onInfo(int fotoResId) {
        // Se carga el fragmento Info en la actividad, agregándolo a la
        // BackStack.
        InfoFragment frg = (InfoFragment) getFragmentManager()
                .findFragmentByTag(TAG_INFO_FRAGMENT);
        if (frg == null) {
            frg = new InfoFragment();
        }
        getFragmentManager().beginTransaction()
                .replace(R.id.frmFragmento, frg, TAG_INFO_FRAGMENT)
                .addToBackStack(TAG_INFO_FRAGMENT).commit();
    }

    // Cuando se solicita la foto.
    @Override
    public void onFoto(int fotoResId) {
        // Se carga el fragmento Foto en la actividad.
        FotoFragment frg = (FotoFragment) getFragmentManager()
                .findFragmentByTag(TAG_FOTO_FRAGMENT);
        if (frg == null) {
            frg = FotoFragment.newInstance(fotoResId);
        }
        getFragmentManager().beginTransaction()
                .replace(R.id.frmFragmento, frg, TAG_FOTO_FRAGMENT).commit();
    }

    // Activa el ítem de overflow en dispositivos con botón físico de menú.
    private void overflowEnDispositivoConTeclaMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ex) {
            // Ignorar
        }
    }

}
