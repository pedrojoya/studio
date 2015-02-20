package es.iessaladillo.pedrojoya.pr028.fragmentos;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import es.iessaladillo.pedrojoya.pr028.R;
import es.iessaladillo.pedrojoya.pr028.bd.BD;
import es.iessaladillo.pedrojoya.pr028.modelos.Alumno;
import es.iessaladillo.pedrojoya.pr028.proveedores.InstitutoContentProvider;

public class ListaAlumnosFragment extends Fragment implements
        LoaderCallbacks<Cursor> {

    // Interfaz de comunicaci�n con la actividad.
    public interface OnListaAlumnosFragmentListener {
        public void onAgregarAlumno();

        public void onEditarAlumno(long id);

        public void onConfirmarEliminarAlumnos();
    }

    // Variables miembro.
    private ListView lstAlumnos;
    private OnListaAlumnosFragmentListener listener;
    private ActionMode modoContextual;
    private SimpleCursorAdapter adaptador;
    private LoaderManager gestor;

    // Retorna la vista que debe mostrar el fragmento.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Se infla el layout que debe mostrar el fragmento.
        View v = inflater.inflate(R.layout.fragment_lista_alumnos, container,
                false);
        // Se configuran las vistas.
        lstAlumnos = (ListView) v.findViewById(R.id.lstAlumnos);
        RelativeLayout rlListaVacia = (RelativeLayout) v.findViewById(R.id.rlListaVacia);
        // Si la lista est� vac�a se muestra un icono y un texto para que al
        // pulsarlo se agregue un alumno.
        rlListaVacia.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Se informa al listener (actividad).
                listener.onAgregarAlumno();
            }
        });
        lstAlumnos.setEmptyView(rlListaVacia);
        // Al hacer click sobre un elemento de la lista.
        lstAlumnos.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // Se obtiene el alumno sobre el que se ha pulsado.
                Cursor c = (Cursor) lstAlumnos.getItemAtPosition(position);
                Alumno alumno = Alumno.fromCursor(c);
                // Se da la orden de editar.
                listener.onEditarAlumno(alumno.getId());
            }

        });
        // Se establece que se puedan seleccionar varios elementos de la lista.
        lstAlumnos.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lstAlumnos.setMultiChoiceModeListener(new MultiChoiceModeListener() {

            @Override
            public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode arg0) {
            }

            // Al crear el modo de acci�n contextual.
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Se infla la especificaci�n del men� contextual en el
                // men�.
                mode.getMenuInflater().inflate(R.menu.fragment_lista_alumnos,
                        menu);
                // Se retorna que ya se ha gestionado el evento.
                return true;
            }

            // Al pulsar sobre un �tem del modo de acci�n contextual.
            @Override
            public boolean onActionItemClicked(ActionMode modo, MenuItem item) {
                // Dependiendo del elemento pulsado.
                switch (item.getItemId()) {
                case R.id.mnuAlumnoEliminar:
                    // Si hay elementos seleccionados se pide confirmaci�n.
                    if (lstAlumnos.getCheckedItemPositions().size() > 0) {
                        // Se almacena el modo contextual para poder cerrarlo
                        // una vez eliminados.
                        modoContextual = modo;
                        // Se pide confirmaci�n.
                        listener.onConfirmarEliminarAlumnos();
                    }
                    break;
                }
                // Se retorna que se ha procesado el evento.
                return true;
            }

            // Al seleccionar un elemento de la lista.
            @Override
            public void onItemCheckedStateChanged(ActionMode mode,
                    int position, long id, boolean checked) {
                // Se actualiza el t�tulo de la action bar contextual.
                mode.setTitle(lstAlumnos.getCheckedItemCount() + "");
            }
        });
        // Se retorna la vista a mostrar.
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Se carga la lista de alumnos.
        cargarAlumnos();
        super.onActivityCreated(savedInstanceState);
    }

    private void cargarAlumnos() {
        // Se inicializa el cargador.
        gestor.initLoader(0, null, this);
        // Se establece un SimpleCursorAdapter como adaptador para la lista, que
        // inicialmente manejar� un cursor nulo.
        String[] from = { BD.Alumno.NOMBRE, BD.Alumno.CURSO,
                BD.Alumno.TELEFONO, BD.Alumno.DIRECCION };
        int[] to = { R.id.lblNombre, R.id.lblCurso, R.id.lblTelefono,
                R.id.lblDireccion };
        adaptador = new SimpleCursorAdapter(this.getActivity(),
                R.layout.fragment_lista_alumnos_item, null, from, to, 0);
        lstAlumnos.setAdapter(adaptador);
    }

    // Cuando el fragmento es cargado en la actividad.
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            // Establece la actividad como objeto listener.
            listener = (OnListaAlumnosFragmentListener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz.
            throw new ClassCastException(activity.toString()
                    + " debe implementar OnElementoSeleccionadoListener");
        }
        // Obtenemos el gestor de cargadores.
        gestor = getActivity().getSupportLoaderManager();
    }

    // Elimina de la base de datos los alumnos seleccionados, actualiza el
    // adaptador y cierra el modo de acci�n conextual.
    public void eliminarAlumnos() {
        // Se obtiene el array con las posiciones seleccionadas.
        SparseBooleanArray seleccionados = lstAlumnos.getCheckedItemPositions();
        // Por cada selecci�n.
        for (int i = 0; i < seleccionados.size(); i++) {
            // Se obtiene la posici�n del elemento en el
            // adaptador.
            int position = seleccionados.keyAt(i);
            // Si ha sido seleccionado
            if (seleccionados.valueAt(i)) {
                // Se obtiene el alumo.
                Cursor cursor = (Cursor) lstAlumnos.getItemAtPosition(position);
                Alumno alu = Alumno.fromCursor(cursor);
                // Se borra de la base de datos a trav�s del content provider.
                Uri uri = Uri
                        .parse(InstitutoContentProvider.CONTENT_URI_ALUMNOS
                                + "/" + alu.getId());
                getActivity().getContentResolver().delete(uri, null, null);
            }
        }
        // Se finaliza el modo contextual.
        modoContextual.finish();
        // Se resetea el cargador del cursor para que actualice la lista.
        // gestor.restartLoader(0, null, this);
    }

    // Cuando se crea el cargador. Retorna el cargador del cursor.
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Se retorna el cargador del cursor. Se le pasa el contexto, la uri en
        // la que consultar los datos y las columnas a obtener.
        return new CursorLoader(getActivity(),
                InstitutoContentProvider.CONTENT_URI_ALUMNOS, BD.Alumno.TODOS,
                null, null, null);
    }

    // Cuando terminan de cargarse los datos en el cargador.
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Se cambia el cursor del adaptador por el que tiene datos.
        adaptador.changeCursor(data);
        // Inicialmente todos los elementos de la lista NO est�n seleccionados.
        for (int i = 0; i < lstAlumnos.getCount(); i++) {
            lstAlumnos.setItemChecked(i, false);
        }
    }

    // Cuando se resetea el cargador.
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Se vac�a de datos el adaptador.
        adaptador.changeCursor(null);
    }
}
