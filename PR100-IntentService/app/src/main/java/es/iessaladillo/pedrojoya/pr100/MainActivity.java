package es.iessaladillo.pedrojoya.pr100;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.software.shell.fab.ActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {

    // Vistas.
    private ObservableListView lstAlumnos;

    // Variables.
    private BroadcastReceiver mExportarReceiver;
    private ArrayAdapter<String> mAdaptador;
    private LocalBroadcastManager mGestor;
    private ActionButton btnExportar;

    // Al crear la actividad.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
        mGestor = LocalBroadcastManager.getInstance(this);
        // Se crea el receptor de mensajes desde el servicio.
        mExportarReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // Se informa de la localización del archivo generado.
                Uri uri = Uri.parse(intent.getStringExtra
                        (ExportarService
                                .EXTRA_FILENAME));
                mostrarSnackbar(uri);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Se registra el receptor para la acción.
        IntentFilter exportarFilter = new IntentFilter(
                ExportarService.ACTION_COMPLETADA);
        mGestor.registerReceiver(mExportarReceiver, exportarFilter);
    }

    @Override
    protected void onPause() {
        // Se desregistra el receptor para dicha acción.
        mGestor.unregisterReceiver(mExportarReceiver);
        super.onPause();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        btnExportar = (ActionButton) findViewById(R.id.btnExportar);
        btnExportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exportar();
            }
        });

        // Se carga la lista a partir de las constantes de cadena.
        lstAlumnos = (ObservableListView) findViewById(R.id.lstAlumnos);
        lstAlumnos.setEmptyView(findViewById(R.id.lblEmpty));
        String[] datosArray = getResources().getStringArray(R.array.alumnos);
        ArrayList<String> datosArrayList = new ArrayList<>(
                Arrays.asList(datosArray));
        mAdaptador = new ArrayAdapter<>(this,
               R.layout.activity_main_item, datosArrayList);
        lstAlumnos.setAdapter(mAdaptador);
        // Se configura el modo de acción contextual.
        lstAlumnos.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lstAlumnos.setMultiChoiceModeListener(new MultiChoiceModeListener() {

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
            }

            // Al crear el modo de acción de contextual.
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Se infla el menú contextual.
                getMenuInflater()
                        .inflate(R.menu.activity_main_contextual, menu);
                // Se retorna que el evento ha sido gestionado.
                return true;
            }

            // Cuando se pulsa un ítem del menú contextual.
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                // Dependiendo del ítem pulsado.
                switch (item.getItemId()) {
                    case R.id.mnuEliminar:
                        eliminarAlumnos(getElementosSeleccionados(lstAlumnos, true));
                        // Se retorna que se ha gestionado el evento.
                        return true;
                }
                return false;
            }

            // Cuando el cambio el estado de selección de un elemento de la
            // lista.
            @Override
            public void onItemCheckedStateChanged(ActionMode mode,
                                                  int position, long id, boolean checked) {
                // Se actualiza el título de la action bar contextual.
                mode.setTitle(lstAlumnos.getCheckedItemCount() + " "
                        + getString(R.string.de) + " " + lstAlumnos.getCount());
            }
        });
        lstAlumnos.setScrollViewCallbacks(this);
    }

    // Exporta la lista de alumnos.
    private void exportar() {
        int numAlumnos = mAdaptador.getCount();
        if (numAlumnos > 0) {
            // Se obtiene la lista de alumnos.
            String[] alumnos = new String[numAlumnos];
            for (int i = 0; i < numAlumnos; i++) {
                alumnos[i] = mAdaptador.getItem(i);
            }
            // Se inicia el servicio enviando como extra el array de datos.
            Intent i = new Intent(this, ExportarService.class);
            i.putExtra(ExportarService.EXTRA_DATOS, alumnos);
            startService(i);
        }
    }

    // Retorna un ArrayList con los elementos seleccionados. Recibe la lista y
    // si debe quitarse la selección una vez obtenidos los elementos.
    private ArrayList<String> getElementosSeleccionados(ListView lst,
            boolean uncheck) {
        // ArrayList resultado.
        ArrayList<String> datos = new ArrayList<>();
        // Se obtienen los elementos seleccionados de la lista.
        SparseBooleanArray selec = lst.getCheckedItemPositions();
        for (int i = 0; i < selec.size(); i++) {
            // Si está seleccionado.
            if (selec.valueAt(i)) {
                int position = selec.keyAt(i);
                // Se quita de la selección (si hay que hacerlo).
                if (uncheck) {
                    lst.setItemChecked(position, false);
                }
                // Se añade al resultado.
                datos.add((String) lst.getItemAtPosition(selec.keyAt(i)));
            }
        }
        // Se retorna el resultado.
        return datos;
    }

    // Elimina de la lista los alumnos recibidos.
    private void eliminarAlumnos(ArrayList<String> elems) {
        // Se eliminan del adaptador.
        for (String elemento : elems) {
            mAdaptador.remove(elemento);
        }
        // Se notifica al adaptador que ha habido cambios.
        mAdaptador.notifyDataSetChanged();
        // Se invalida el menú de opciones para que puede desactivar la opción
        // de exportar si no hay alumnos.
        if (mAdaptador.getCount() <= 0) {
            invalidateOptionsMenu();
        }
    }

    // Muestra una snackbar con el mensaje y la accion deshacer.
    private void mostrarSnackbar(final Uri uri) {
        SnackbarManager.show(
                Snackbar.with(this)
                        .text(getString(R.string.listado_exportado))
                        .actionLabel(getString(R.string.abrir))
                        .actionColorResource(R.color.accent)
                        .actionListener(new ActionClickListener() {
                            @Override
                            public void onActionClicked(Snackbar snackbar) {
                                verArchivo(uri);
                            }
                        })
                        .duration(Snackbar.SnackbarDuration.LENGTH_LONG)
        );
    }

    // Envía un intent implícito para ver el archivo.
    private void verArchivo(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "text/plain");
        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onScrollChanged(int i, boolean b, boolean b1) {
    }

    @Override
    public void onDownMotionEvent() {
    }

    // Cuando se mueve el scroll de la lista hacia abajo o arriba.
    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        ActionBar ab = getSupportActionBar();
        if (scrollState == ScrollState.UP) {
            if (ab != null && ab.isShowing()) {
                ab.hide();
            }
            btnExportar.hide();
        } else if (scrollState == ScrollState.DOWN) {
            if (ab != null && !ab.isShowing()) {
                ab.show();
            }
            btnExportar.show();
        }
    }

}
