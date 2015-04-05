package es.iessaladillo.pedrojoya.pr118.actividades;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

import es.iessaladillo.pedrojoya.pr118.R;
import es.iessaladillo.pedrojoya.pr118.datos.BusquedaProvider;


public class MainActivity extends ActionBarActivity {

    private Toolbar mToolbar;
    private SearchView svBuscar;
    private MenuItem mnuLimpiar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se activa el ítem de overflow en dispositivos con botón físico de
        // menú.
        overflowEnDispositivoConTeclaMenu();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_burger);
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        // Se obtiene y configura el SearchView en base al archivo XML de configuración.
        svBuscar = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.mnuBuscar));
        mnuLimpiar = menu.findItem(R.id.mnuLimpiar);
        SearchManager gestorBusquedas = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        svBuscar.setSearchableInfo(gestorBusquedas.getSearchableInfo(
                new ComponentName(this, BusquedaActivity.class)));
        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.mnuBuscar),
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem menuItem) {
                        mnuLimpiar.setVisible(false);
                        mToolbar.setNavigationIcon(R.drawable.ic_back);
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                        mToolbar.setNavigationIcon(R.drawable.ic_burger);
                        mnuLimpiar.setVisible(true);
                        return true;
                    }
                });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            svBuscar.onActionViewCollapsed();
            return true;
        }
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
