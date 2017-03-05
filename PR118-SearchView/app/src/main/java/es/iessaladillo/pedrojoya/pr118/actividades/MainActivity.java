package es.iessaladillo.pedrojoya.pr118.actividades;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;

import java.lang.reflect.Field;

import es.iessaladillo.pedrojoya.pr118.R;
import es.iessaladillo.pedrojoya.pr118.datos.BusquedaProvider;


@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private SearchView svBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se activa el ítem de overflow en dispositivos con botón físico de menú.
        overflowEnDispositivoConTeclaMenu();
        // La toolbar actuará como action bar.
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_burger);
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        // Se obtiene y configura el SearchView en base al archivo XML de configuración.
        svBuscar = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.mnuBuscar));
        svBuscar.setIconifiedByDefault(true);
        svBuscar.setSubmitButtonEnabled(false);
        SearchManager gestorBusquedas = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        svBuscar.setSearchableInfo(gestorBusquedas.getSearchableInfo(
                new ComponentName(this, BusquedaActivity.class)));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mnuLimpiar) {
            limpiarHistorialBusqueda();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Limpia el historial de búsquedas en la aplicación.
    private void limpiarHistorialBusqueda() {
        // Se obtiene el objeto de sugerencias.
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                BusquedaProvider.AUTHORITY, BusquedaProvider.MODE);
        // Se limpia el historial.
        suggestions.clearHistory();
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
