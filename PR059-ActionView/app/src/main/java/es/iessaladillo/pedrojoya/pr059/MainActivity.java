package es.iessaladillo.pedrojoya.pr059;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements SearchView.OnQueryTextListener,
        OnItemClickListener {

    // Variables.
    private ArrayAdapter<String> adaptador;

    // Cuando se crea la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        ListView lstAlumnos = (ListView) findViewById(R.id.lstAlumnos);
        // Se llena de datos la lista.
        String[] alumnos = getResources().getStringArray(R.array.alumnos);
        adaptador = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, alumnos);
        lstAlumnos.setAdapter(adaptador);
        // La actividad actuará como listener cuando se pulse sobre un elemento.
        lstAlumnos.setOnItemClickListener(this);
    }

    // Al crear la primera vez el menú.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Se infla el menú a partir del XML.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        // Se obtiene la referencia a la SearchView.
        SearchView svBuscar = (SearchView) menu.findItem(R.id.mnuBuscar).getActionView();
        // La propia actividad será notificada cuando de realice la búsqueda.
        svBuscar.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    // Cuando se cambia el texto en el SearchView.
    @Override
    public boolean onQueryTextChange(String s) {
        // Se filta la lista por el texto introducido. (ArrayAdapter ya
        // implementa la interfaz Filterable. Para un adaptador más complejo se
        // recomienda ver el proyecto sobre AutoCompleteTextView).
        adaptador.getFilter().filter(s);
        // Se indica que ya se ha consumido el evento.
        return true;
    }

    // Cuando se envía el término a la búsqueda.
    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(getApplicationContext(),
                getString(R.string.buscar) + " " + query, Toast.LENGTH_SHORT)
                .show();
        // Se indica que ya se ha consumido el evento.
        return true;
    }

    // Cuando se hace click en un elemento de la lista
    @Override
    public void onItemClick(AdapterView<?> lst, View v, int position, long id) {
        // Se informa al usuario sobre que alumno ha pulsado.
        Toast.makeText(
                lst.getContext(),
                getResources().getString(R.string.ha_pulsado_sobre)
                        + lst.getItemAtPosition(position), Toast.LENGTH_SHORT)
                .show();
    }

}
