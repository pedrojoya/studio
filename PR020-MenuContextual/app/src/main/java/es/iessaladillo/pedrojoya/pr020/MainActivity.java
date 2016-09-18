package es.iessaladillo.pedrojoya.pr020;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView lstAlumnos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
    }

    private void initVistas() {
        lstAlumnos = (ListView) findViewById(R.id.lstAlumnos);
        // Se crea el adaptador con los datos obtenidos a partir de un recurso
        // array de cadena y un layout estándar.
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.alumnos));
        lstAlumnos.setAdapter(adaptador);
        lstAlumnos.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lst, View vistafila,
                    int posicion, long id) {
                mostrarMensaje(getString(R.string.ha_pulsado_sobre,
                        (String) lstAlumnos.getItemAtPosition(posicion)));
            }
        });
        // Se registra el ListView para que tenga menú contextual.
        registerForContextMenu(lstAlumnos);
    }

    // Cuando se debe crear el menú contextual.
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        // Si se ha hecho LongClick sobre la lista.
        if (v.getId() == R.id.lstAlumnos) {
            // Se obtiene la posición de la lista en la que se ha pulsado.
            int position = ((AdapterContextMenuInfo) menuInfo).position;
            // Se modifican los ítems del menú para que aparezca el nombre
            // sobre el que se ha pulsado.
            getMenuInflater().inflate(R.menu.activity_main_contextual, menu);
            menu.findItem(R.id.mnuEditar).setTitle(
                    getString(R.string.editar, lstAlumnos.getItemAtPosition(position)));
            menu.findItem(R.id.mnuEliminar).setTitle(
                    getString(R.string.eliminar, lstAlumnos.getItemAtPosition(position)));
            // Se establece el título del menú.
            menu.setHeaderTitle(R.string.elija_una_opcion);
        }
        // Se llama al padre por si quiere añadir algún elemento.
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    // Cuando se ha seleccionado un elemento del menú contextual.
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Dependiendo de la opción seleccionada
        int position = ((AdapterContextMenuInfo) item.getMenuInfo()).position;
        switch (item.getItemId()) {
            case R.id.mnuEditar:
                mostrarMensaje(getString(R.string.editar, lstAlumnos.getItemAtPosition(position)));
                break;
            case R.id.mnuEliminar:
                mostrarMensaje(getString(R.string.eliminar,
                        lstAlumnos.getItemAtPosition(position)));
                break;
            default:
                // Retorno lo que retorne el padre.
                return super.onContextItemSelected(item);
        }
        // Se indica que ya se ha gestionado el evento.
        return true;
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT)
                .show();
    }

}
