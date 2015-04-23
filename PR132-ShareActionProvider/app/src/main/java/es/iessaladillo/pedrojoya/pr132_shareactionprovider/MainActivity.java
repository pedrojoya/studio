package es.iessaladillo.pedrojoya.pr132_shareactionprovider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<String> mAdaptador;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        // Se carga la lista a partir de las constantes de cadena.
        ListView lstAlumnos = (ListView) findViewById(R.id.lstAlumnos);
        lstAlumnos.setEmptyView(findViewById(R.id.lblEmpty));
        String[] datosArray = getResources().getStringArray(R.array.alumnos);
        ArrayList<String> datosArrayList = new ArrayList<>(
                Arrays.asList(datosArray));
        mAdaptador = new ArrayAdapter<>(this,
                R.layout.activity_main_item, datosArrayList);
        lstAlumnos.setAdapter(mAdaptador);
        lstAlumnos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long id) {
                eliminar(mAdaptador.getItem(position));
            }
        });
    }

    // Al crear el menú de opciones.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        mShareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(menu
                        .findItem(R.id.mnuShare));
        mShareActionProvider.setShareIntent(getIntentCompartir());
        return super.onCreateOptionsMenu(menu);
    }

    // Retorna el intent con los datos para el share action provider.
    private Intent getIntentCompartir() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, getListado());
        return intent;
    }

    // Retorna una cadena con la lista de alumnos.
    private String getListado() {
        StringBuilder sb = new StringBuilder();
        sb.append("");
        for (int i = 0; i < mAdaptador.getCount(); i++) {
            sb.append(mAdaptador.getItem(i)).append("\n");
        }
        return sb.toString();
    }

    // Elimina de la lista el elemento recibido.
    private void eliminar(String elemento) {
        mAdaptador.remove(elemento);
        // Se actualiza el intent de compartición.
        mShareActionProvider.setShareIntent(getIntentCompartir());
    }

}
