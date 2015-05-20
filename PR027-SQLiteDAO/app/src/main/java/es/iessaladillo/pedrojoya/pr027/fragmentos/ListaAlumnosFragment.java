package es.iessaladillo.pedrojoya.pr027.fragmentos;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr027.R;
import es.iessaladillo.pedrojoya.pr027.adaptadores.ListaAlumnosAdapter;
import es.iessaladillo.pedrojoya.pr027.bd.DAO;
import es.iessaladillo.pedrojoya.pr027.modelos.Alumno;

public class ListaAlumnosFragment extends Fragment {

    private ListaAlumnosAdapter mAdaptador;

    // Interfaz de comunicación con la actividad.
    public interface OnListaAlumnosFragmentListener {
        public void onAgregarAlumno();

        public void onEditarAlumno(long id);

        public void onConfirmarEliminarAlumnos();
    }

    // Variables miembro.
    private ListView lstAlumnos;
    private OnListaAlumnosFragmentListener listener;
    private ActionMode modoContextual;

    // Retorna la vista que debe mostrar el fragmento.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Se infla y se retorna el layout que debe mostrar el fragmento.
        return inflater.inflate(R.layout.fragment_lista_alumnos, container,
                false);
    }

    // Cuando la actividad se ha creado por completo.
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Se obtienen e inicializan las vistas.
        initVistas(getView());
    }

    // Obtiene e inicializa las vistas.
    private void initVistas(View v) {
        // Se configuran las vistas.
        lstAlumnos = (ListView) v.findViewById(R.id.lstAlumnos);
        RelativeLayout rlListaVacia = (RelativeLayout) v.findViewById(R.id.rlListaVacia);
        // Si la lista está vacía se muestra un icono y un texto para que al
        // pulsarlo se agregue un alumno.
        rlListaVacia.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Se informa al listener (actividad) de que se quiere agregar
                // un alumno.
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
                Alumno alumno = (Alumno) lstAlumnos.getItemAtPosition(position);
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

            // Al crear el modo de acción contextual.
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Se infla la especificación del menú contextual en el
                // menú.
                mode.getMenuInflater().inflate(R.menu.fragment_lista_alumnos,
                        menu);
                // Se retorna que ya se ha gestionado el evento.
                return true;
            }

            // Al pulsar sobre un ítem del modo de acción contextual.
            @Override
            public boolean onActionItemClicked(ActionMode modo, MenuItem item) {
                // Dependiendo del elemento pulsado.
                switch (item.getItemId()) {
                    case R.id.mnuAlumnoEliminar:
                        // Si hay elementos seleccionados se pide confirmación.
                        if (lstAlumnos.getCheckedItemPositions().size() > 0) {
                            // Se almacena el modo contextual para poder cerrarlo
                            // una vez eliminados.
                            modoContextual = modo;
                            // Se pide confirmación.
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
                // Se actualiza el título de la action bar contextual.
                mode.setTitle(lstAlumnos.getCheckedItemCount() + "");
            }
        });
        (v.findViewById(R.id.btnAgregar)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // Se informa al listener (actividad) de que se quiere agregar
                // un alumno.
                listener.onAgregarAlumno();
            }
        });
    }

    // Al mostrar el fragmento.
    @Override
    public void onResume() {
        // Se carga la lista de alumnos.
        cargarAlumnos();
        super.onResume();
    }

    // Crea el adaptador y carga la lista de alumnos.
    private void cargarAlumnos() {
        // Se obtienen los datos de los alumnos a través del DAO.
        ArrayList<Alumno> alumnos = (ArrayList<Alumno>) (new DAO(getActivity()))
                .getAllAlumnos();
        // Se establece el adaptador para la lista (personalizado).
        mAdaptador = new ListaAlumnosAdapter(getActivity(), alumnos);
        lstAlumnos.setAdapter(mAdaptador);
    }

    // Cuando el fragmento es cargado en la actividad.
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            // Se establece la actividad como objeto listener.
            listener = (OnListaAlumnosFragmentListener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz.
            throw new ClassCastException(activity.toString()
                    + " debe implementar OnElementoSeleccionadoListener");
        }
    }

    // Elimina de la base de datos los alumnos seleccionados, actualiza el
    // adaptador y cierra el modo de acción conextual.
    public void eliminarAlumnos() {
        // Se obtienen los elementos seleccionados (y se
        // quita la selección).
        ArrayList<Alumno> elems = getElementosSeleccionados(
                lstAlumnos, true);
        // Se eliminan de la base de datos y del adaptador.
        for (Alumno alumno : elems) {
            // Se borra de la base de datos.
            (new DAO(getActivity())).deleteAlumno(alumno.getId());
            mAdaptador.remove(alumno);
        }
        // Se notifica al adaptador que ha habido cambios.
        mAdaptador.notifyDataSetChanged();
        // Se finaliza el modo contextual.
        modoContextual.finish();
    }

    // Retorna un ArrayList con los elementos seleccionados. Recibe la lista y
    // si debe quitarse la selección una vez obtenidos los elementos.
    private ArrayList<Alumno> getElementosSeleccionados(ListView lst,
                                                        boolean uncheck) {
        // ArrayList resultado.
        ArrayList<Alumno> datos = new ArrayList<>();
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
                datos.add((Alumno) lst.getItemAtPosition(selec.keyAt(i)));
            }
        }
        // Se retorna el resultado.
        return datos;
    }

}