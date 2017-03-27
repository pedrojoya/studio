package es.iessaladillo.pedrojoya.pr101;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewCompat;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class MainFragment extends Fragment {

    private ListView lstAlumnos;
    private BroadcastReceiver mExportarReceiver;
    private ArrayAdapter<String> mAdaptador;

    public MainFragment() { }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initVistas(getView());
        setupReceiver();
    }

    private void setupReceiver() {
        // Se crea el receptor de mensajes desde el servicio.
        mExportarReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // Se informa de la localización del archivo generado.
                Uri uri = intent.getParcelableExtra(ExportarService
                        .EXTRA_FILENAME);
                mostrarSnackbar(uri);
            }
        };
    }

    // Muestra una snackbar con el mensaje y la accion deshacer.
    private void mostrarSnackbar(final Uri uri) {
        Snackbar.make(lstAlumnos, R.string.listado_exportado, Snackbar.LENGTH_LONG)
                .setAction(R.string.abrir, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        verArchivo(uri);
                    }
                }).show();
    }

    // Envía un intent implícito para ver el archivo.
    private void verArchivo(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uriProvider = FileProvider
                .getUriForFile(getActivity(),
                        "es.iessaladillo.pedrojoya.pr101.fileprovider",
                        new File(uri.getPath()));

        intent.setDataAndType(uriProvider, "text/plain");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initVistas(View view) {
        setupListView(view);
    }

    private void setupListView(View view) {
        // Se carga la lista a partir de las constantes de cadena.
        if (mAdaptador == null) {
            String[] datosArray = getResources().getStringArray(R.array.alumnos);
            ArrayList<String> datosArrayList = new ArrayList<>(
                    Arrays.asList(datosArray));
            mAdaptador = new ArrayAdapter<>(getActivity(),
                    R.layout.fragment_main_item, datosArrayList);
        }
        lstAlumnos = (ListView) view.findViewById(R.id.lstAlumnos);
        if (lstAlumnos != null) {
            lstAlumnos.setEmptyView(view.findViewById(R.id.lblEmpty));
            lstAlumnos.setAdapter(mAdaptador);
            // Se configura el modo de acción contextual.
            lstAlumnos.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            lstAlumnos.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

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
                    getActivity().getMenuInflater()
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
                    mode.setTitle(getString(R.string.de,
                            lstAlumnos.getCheckedItemCount(), lstAlumnos.getCount()));
                }
            });
            // API 21+ funcionará con CoordinatorLayout.
            ViewCompat.setNestedScrollingEnabled(lstAlumnos, true);
        }

    }

    // Exporta la lista de alumnos.
    public void exportar() {
        int numAlumnos = mAdaptador.getCount();
        if (numAlumnos > 0) {
            // Se obtiene la lista de alumnos.
            String[] alumnos = new String[numAlumnos];
            for (int i = 0; i < numAlumnos; i++) {
                alumnos[i] = mAdaptador.getItem(i);
            }
            // Se inicia el servicio enviando como extra el array de datos.
            Intent i = new Intent(getActivity(), ExportarService.class);
            i.putExtra(ExportarService.EXTRA_DATOS, alumnos);
            getActivity().startService(i);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Se registra el receptor para la acción.
        IntentFilter exportarFilter = new IntentFilter(ExportarService.ACTION_COMPLETADA);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mExportarReceiver, exportarFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Se quita del registro el receptor.
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mExportarReceiver);
    }

    // Retorna un ArrayList con los elementos seleccionados. Recibe la lista y
    // si debe quitarse la selección una vez obtenidos los elementos.
    @SuppressWarnings("SameParameterValue")
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
    }

}
