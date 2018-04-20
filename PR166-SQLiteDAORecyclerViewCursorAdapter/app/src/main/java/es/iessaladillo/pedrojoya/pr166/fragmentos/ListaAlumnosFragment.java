package es.iessaladillo.pedrojoya.pr166.fragmentos;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
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

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr166.R;
import es.iessaladillo.pedrojoya.pr166.adaptadores.AlumnosAdapter;
import es.iessaladillo.pedrojoya.pr166.bd.DAO;
import es.iessaladillo.pedrojoya.pr166.modelos.Alumno;
import es.iessaladillo.pedrojoya.pr166.utils.HidingScrollListener;

public class ListaAlumnosFragment extends Fragment implements AlumnosAdapter
        .OnItemLongClickListener, ActionMode.Callback, AlumnosAdapter.OnItemClickListener {

    private DAO mDao;
    private TextView lblNuevoAlumno;
    private AlumnosAdapter mAdaptador;
    private ActionMode mActionMode;
    private HidingScrollListener mHidingScrollListener;

    // Interfaz de comunicación con la actividad.
    public interface OnListaAlumnosFragmentListener {

        void onAgregarAlumno();

        void onEditarAlumno(long id);

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
        return inflater.inflate(R.layout.fragment_lista_alumnos, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initVistas(getView());
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
        if (mAdaptador == null) {
            mAdaptador = new AlumnosAdapter();
        }
        mAdaptador.setOnItemClickListener(this);
        mAdaptador.setOnItemLongClickListener(this);
        mAdaptador.setOnEmptyStateChangedListener(new AlumnosAdapter.OnEmptyStateChangedListener() {
            @Override
            public void onEmptyStateChanged(boolean isEmpty) {
                lblNuevoAlumno.setVisibility(isEmpty ? View.VISIBLE : View.INVISIBLE);
            }
        });
        lstAlumnos.setAdapter(mAdaptador);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(requireActivity(),
                LinearLayoutManager.VERTICAL, false);
        lstAlumnos.setLayoutManager(mLayoutManager);
        lstAlumnos.addItemDecoration(
                new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
        lstAlumnos.setItemAnimator(new DefaultItemAnimator());
        // Drag & drop y Swipe to dismiss.
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
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

    // Al mostrar el fragmento.
    @Override
    public void onResume() {
        // Se obtiene la BD.
        mDao = new DAO(requireActivity());
        // Se carga la lista de alumnos.
        cargarAlumnos();
        super.onResume();
    }

    @Override
    public void onPause() {
        // Se cierra la base de datos.
        mDao.closeDatabase();
        super.onPause();
    }

    // Crea el adaptador y carga la lista de alumnos.
    private void cargarAlumnos() {
        // Se obtienen los datos de los alumnos a través del DAO.
        Cursor cursor = mDao.queryAllAlumnos(mDao.openWritableDatabase());
        mAdaptador.swapCursor(cursor);
    }

    // Cuando el fragmento es cargado en la actividad.
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            // Se establece la actividad como objeto listener.
            listener = (OnListaAlumnosFragmentListener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz.
            throw new ClassCastException(
                    activity.toString() + " debe implementar OnElementoSeleccionadoListener");
        }
    }

    // Elimina de la base de datos los alumnos seleccionados, actualiza el
    // adaptador y cierra el modo de acción conextual.
    public void eliminarAlumnosSeleccionados() {
        // Se cierra la bd.
        mDao.closeDatabase();
        // Se obtiene el array con las posiciones seleccionadas.
        ArrayList<Integer> seleccionados = mAdaptador.getSelectedItemsPositions();
        // Por cada selección.
        for (int i = 0; i < seleccionados.size(); i++) {
            // Se obtiene el alumo.
            Alumno alu = DAO.cursorToAlumno(mAdaptador.getItemAtPosition(seleccionados.get(i)));
            // Se borra de la base de datos.
            mDao.deleteAlumno(alu.getId());
        }
        // Se finaliza el modo contextual.
        mActionMode.finish();
        // Se obtiene el cursor actualizado.
        Cursor cursor = mDao.queryAllAlumnos(mDao.openWritableDatabase());
        // Se actualiza el cursor del adaptador.
        mAdaptador.swapCursor(cursor);
    }

    private void eliminarAlumno(int position) {
        // Se cierra la bd.
        mDao.closeDatabase();
        // Se obtiene el alumo.
        Alumno alu = DAO.cursorToAlumno(mAdaptador.getItemAtPosition(position));
        // Se borra de la base de datos.
        mDao.deleteAlumno(alu.getId());
        // Se obtiene el cursor actualizado.
        Cursor cursor = mDao.queryAllAlumnos(mDao.openWritableDatabase());
        // Se actualiza el cursor del adaptador.
        mAdaptador.swapCursor(cursor);
    }

    @Override
    public void onItemClick(View view, Cursor cursor, int position) {
        if (mActionMode != null) {
            toggleSelection(position);
        } else {
            // Se obtiene el alumno sobre el que se ha pulsado.
            Alumno alumno = DAO.cursorToAlumno(cursor);
            // Se da la orden de editar.
            listener.onEditarAlumno(alumno.getId());
        }
    }

    @Override
    public void onItemLongClick(View view, Cursor cursor, int position) {
        if (mActionMode != null) {
            toggleSelection(position);
        } else {
            mActionMode = requireActivity().startActionMode(this);
            toggleSelection(position);
            listener.onHideFAB();
        }
    }

    // Cambia el estado de selección del elemento situado en dicha posición.
    private void toggleSelection(int position) {
        // Se cambia el estado de selección
        mAdaptador.toggleSelection(position);
        // Se actualiza el texto del action mode contextual.
        mActionMode.setTitle(mAdaptador.getSelectedItemCount() + " / " + mAdaptador.getItemCount());
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
        actionMode.getMenuInflater().inflate(R.menu.fragment_lista_alumnos, menu);
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
}
