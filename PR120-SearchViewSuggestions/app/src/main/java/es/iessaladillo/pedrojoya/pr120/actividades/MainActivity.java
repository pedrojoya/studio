package es.iessaladillo.pedrojoya.pr120.actividades;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import es.iessaladillo.pedrojoya.pr120.R;
import es.iessaladillo.pedrojoya.pr120.datos.InstitutoHelper;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InstitutoHelper helper = new InstitutoHelper(getApplicationContext());
        SQLiteDatabase bd = helper.getWritableDatabase();
        bd.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        // Se obtiene y configura el SearchView en base al archivo XML de configuración.
        SearchView svBuscar = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.mnuBuscar));
        SearchManager gestorBusquedas = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        svBuscar.setSearchableInfo(gestorBusquedas.getSearchableInfo(
                new ComponentName(this, BusquedaActivity.class)));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mnuBuscar) {
            // Se consume el evento mostrando el actionview correspondiente al SearchView.
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
