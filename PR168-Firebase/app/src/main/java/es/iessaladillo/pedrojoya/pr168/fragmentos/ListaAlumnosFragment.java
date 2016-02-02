package es.iessaladillo.pedrojoya.pr168.fragmentos;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr168.App;
import es.iessaladillo.pedrojoya.pr168.R;
import es.iessaladillo.pedrojoya.pr168.adaptadores.AlumnosAdapter;
import es.iessaladillo.pedrojoya.pr168.modelos.Alumno;
import es.iessaladillo.pedrojoya.pr168.utils.DividerItemDecoration;
import es.iessaladillo.pedrojoya.pr168.utils.HidingScrollListener;

public class ListaAlumnosFragment extends Fragment implements AlumnosAdapter.OnItemLongClickListener, ActionMode.Callback, AlumnosAdapter.OnItemClickListener {

    private static final String STATE_LISTA = "state_lista";
    private static final String STATE_DATOS = "state_datos";

    private TextView lblNuevoAlumno;
    private AlumnosAdapter mAdaptador;
    private ActionMode mActionMode;
    private HidingScrollListener mHidingScrollListener;
    private LinearLayoutManager mLayoutManager;
    private Parcelable mEstadoLista;
    private RecyclerView.AdapterDataObserver mObservador;

    // Interfaz de comunicación con la actividad.
    public interface OnListaAlumnosFragmentListener {

        void onAgregarAlumno();

        void onEditarAlumno(String key);

        void onConfirmarEliminarAlumnos();

        void onShowFAB();

        void onHideFAB();

    }

    private OnListaAlumnosFragmentListener listener;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lista_alumnos, container,
                false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initVistas(getView());
        if (savedInstanceState != null) {
            // Se obtiene el estado anterior de la lista y los datos.
            mEstadoLista = savedInstanceState.getParcelable(STATE_LISTA);
        }
    }

    // Obtiene e inicializa las vistas.
    private void initVistas(View v) {
        lblNuevoAlumno = (TextView) v.findViewById(R.id.lblNuevoAlumno);
        lblNuevoAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAgregarAlumno();
            }
        });
        RecyclerView lstAlumnos = (RecyclerView) v.findViewById(R.id.lstAlumnos);
        lstAlumnos.setHasFixedSize(true);
        Firebase ref = new Firebase(App.FIREBASE_URL);
        mAdaptador = new AlumnosAdapter(ref);
        mAdaptador.setOnItemClickListener(this);
        mAdaptador.setOnItemLongClickListener(this);
        mObservador = new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                lblNuevoAlumno.setVisibility(mAdaptador.isEmpty() ? View.VISIBLE : View.INVISIBLE);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                lblNuevoAlumno.setVisibility(mAdaptador.isEmpty() ? View.VISIBLE : View.INVISIBLE);
            }

            @Override
            public void onChanged() {
                super.onChanged();
                lblNuevoAlumno.setVisibility(mAdaptador.isEmpty() ? View.VISIBLE : View.INVISIBLE);
            }
        };
        mAdaptador.registerAdapterDataObserver(mObservador);
        lstAlumnos.setAdapter(mAdaptador);
        mLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        lstAlumnos.setLayoutManager(mLayoutManager);
        lstAlumnos.addItemDecoration(
                new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        lstAlumnos.setItemAnimator(new DefaultItemAnimator());
        // Drag & drop y Swipe to dismiss.
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP |
                        ItemTouchHelper.DOWN,
                        ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        // Se elimina el elemento.
                        eliminarAlumno(viewHolder.getAdapterPosition());
                    }
                });
        itemTouchHelper.attachToRecyclerView(lstAlumnos);
        // Hide / show FAB on scrolling.
        mHidingScrollListener = new HidingScrollListener() {

            @Override
            public void onHide() {
                listener.onHideFAB();
            }

            @Override
            public void onShow() {
                listener.onShowFAB();
            }
        };
        lstAlumnos.addOnScrollListener(mHidingScrollListener);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Se salva el estado del RecyclerView.
        mEstadoLista = mLayoutManager.onSaveInstanceState();
        outState.putParcelable(STATE_LISTA, mEstadoLista);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Se retaura el estado de la lista.
        lblNuevoAlumno.setVisibility(mAdaptador.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        if (mEstadoLista != null) {
            mLayoutManager.onRestoreInstanceState(mEstadoLista);
        }
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
    public void eliminarAlumnosSeleccionados() {
        // Se obtiene el array con las posiciones seleccionadas.
        ArrayList<Integer> seleccionados = mAdaptador.getSelectedItemsPositions();
        // Por cada selección.
        for (int i = 0; i < seleccionados.size(); i++) {
            // Se obtiene la referencia al alumno.
            Firebase refAlumno = mAdaptador.getRef(seleccionados.get(i));
            // Se elimina el alumo de la bd.
            refAlumno.removeValue();
        }
        lblNuevoAlumno.setVisibility(mAdaptador.isEmpty()?View.VISIBLE:View.INVISIBLE);
        // Se finaliza el modo contextual.
        mActionMode.finish();
    }

    private void eliminarAlumno(int position) {
        // Se obtiene la referencia al alumno.
        Firebase refAlumno = mAdaptador.getRef(position);
        // Se borra de la base de datos.
        refAlumno.removeValue();
        lblNuevoAlumno.setVisibility(mAdaptador.isEmpty() ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onItemClick(View view, Alumno alumno, String key, int position) {
        if (mActionMode != null) {
            toggleSelection(position);
        } else {
            // Se da la orden de editar.
            listener.onEditarAlumno(key);
        }
    }

    @Override
    public void onItemLongClick(View view, Alumno alumno, int position) {
        if (mActionMode != null) {
            toggleSelection(position);
        } else {
            mActionMode = getActivity().startActionMode(this);
            toggleSelection(position);
            listener.onHideFAB();
        }
    }

    // Cambia el estado de selección del elemento situado en dicha posición.
    private void toggleSelection(int position) {
        // Se cambia el estado de selección
        mAdaptador.toggleSelection(position);
        // Se actualiza el texto del action mode contextual.
        mActionMode.setTitle(mAdaptador.getSelectedItemCount() + " / " +
                mAdaptador.getItemCount());
        // Si ya no hay ningún elemento seleccionado se finaliza el modo de
        // acción contextual
        if (mAdaptador.getSelectedItemCount() == 0) {
            mActionMode.finish();
        }
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        // Se infla la especificación del menú contextual en el
        // menú.
        actionMode.getMenuInflater().inflate(R.menu.fragment_lista_alumnos,
                menu);
        // Se retorna que ya se ha gestionado el evento.
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        // Dependiendo del elemento pulsado.
        switch (menuItem.getItemId()) {
            case R.id.mnuAlumnoEliminar:
                // Si hay elementos seleccionados se pide confirmación.
                if (mAdaptador.getSelectedItemCount() > 0) {
                    // Se almacena el modo contextual para poder cerrarlo
                    // una vez eliminados.
                    mActionMode = actionMode;
                    // Se pide confirmación.
                    listener.onConfirmarEliminarAlumnos();
                }
                break;
        }
        // Se retorna que se ha procesado el evento.
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        mAdaptador.clearSelections();
        mActionMode = null;
        listener.onShowFAB();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mActionMode != null) {
            mActionMode.finish();
        }
        mHidingScrollListener.reset();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdaptador.unregisterAdapterDataObserver(mObservador);
        mAdaptador.cleanup();
    }
}
