package es.iessaladillo.pedrojoya.pr118.actividades;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;

import java.lang.reflect.Field;

import es.iessaladillo.pedrojoya.pr118.R;
import es.iessaladillo.pedrojoya.pr118.datos.BusquedaProvider;


public class MainActivity extends ActionBarActivity {

    private Toolbar mSearchToolbar;
    private Toolbar mToolbar;
    private SearchView svBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se activa el ítem de overflow en dispositivos con botón físico de
        // menú.
        overflowEnDispositivoConTeclaMenu();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        // Se configura la Search Toolbar.
        configSearchToolbar();
    }

    private void configSearchToolbar() {
        mSearchToolbar = (Toolbar) findViewById(R.id.searchToolbar);
        mSearchToolbar.setVisibility(View.INVISIBLE);
        mSearchToolbar.setNavigationIcon(R.drawable.ic_back);
        mSearchToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mToolbar.setVisibility(View.VISIBLE);
                mSearchToolbar.setVisibility(View.INVISIBLE);
            }
        });
        // Se obtiene el SearchView.
        svBuscar = (SearchView) findViewById(R.id.svBuscar);
        // Se obtiene el gestor de búsquedas.
        SearchManager gestorBusquedas = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        // Se obtiene la configuración de la actividad de búsqueda y se le asigna al SearchView.
        svBuscar.setSearchableInfo(gestorBusquedas.getSearchableInfo(
                new ComponentName(this, BusquedaActivity.class)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mnuBuscar) {
            mSearchToolbar.setVisibility(View.VISIBLE);
            mToolbar.setVisibility(View.INVISIBLE);
            svBuscar.onActionViewExpanded();
/*
            svBuscar.post(new Runnable() {
                @Override
                public void run() {
                    ImageView v = (ImageView) svBuscar.findViewById(R.id.search_mag_icon);
                    if (v != null) {
                        v.setVisibility(View.GONE);
                    }

                }
            });
*/
            return true;
        }
        if (id == R.id.mnuLimpiar) {
            limpiarHistorialBusqueda();
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
