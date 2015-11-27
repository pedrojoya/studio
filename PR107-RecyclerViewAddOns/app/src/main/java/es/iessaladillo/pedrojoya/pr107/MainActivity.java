package es.iessaladillo.pedrojoya.pr107;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements
        AlumnosAdapter.OnItemClickListener, AlumnosAdapter.OnItemLongClickListener,
        ActionMode.Callback {

    private static final String STATE_LISTA = "estadoLista";

    private RecyclerView lstAlumnos;
    private FloatingActionButton fabAccion;
    private AlumnosAdapter mAdaptador;
    private TextView lblNoHayAlumnos;
    private ActionMode mActionMode;
    private Toolbar toolbar;
    private LinearLayoutManager mLayoutManager;
    private Parcelable mEstadoLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        configToolbar();
        configRecyclerView();
        configFab();
    }

    // Configura la Toolbar.
    private void configToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    // Configura el FAB.
    private void configFab() {
        fabAccion = (FloatingActionButton) findViewById(R.id.fabAccion);
        fabAccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarAlumno(DB.getNextAlumno());
            }
        });
    }

    // Configura el RecyclerView.
    private void configRecyclerView() {
        lblNoHayAlumnos = (TextView) findViewById(R.id.lblNoHayAlumnos);
        lstAlumnos = (RecyclerView) findViewById(R.id.lstAlumnos);
        lstAlumnos.setHasFixedSize(true);
        mAdaptador = new AlumnosAdapter(DB.getAlumnos());
        mAdaptador.setOnItemClickListener(this);
        mAdaptador.setOnItemLongClickListener(this);
        mAdaptador.setOnEmptyStateChangedListener(new AlumnosAdapter.OnEmptyStateChangedListener() {
            @Override
            public void onEmptyStateChanged(boolean isEmpty) {
                lblNoHayAlumnos.setVisibility(isEmpty ? View.VISIBLE : View.INVISIBLE);
            }
        });
        lstAlumnos.setAdapter(mAdaptador);
        mLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        lstAlumnos.setLayoutManager(mLayoutManager);
        lstAlumnos.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL));
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
                        final int fromPos = viewHolder.getAdapterPosition();
                        final int toPos = target.getAdapterPosition();
                        mAdaptador.swapItems(fromPos, toPos);
                        return true;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        // Se elimina el elemento.
                        mAdaptador.removeItem(viewHolder.getAdapterPosition());
                    }
                });
        itemTouchHelper.attachToRecyclerView(lstAlumnos);
        // Hide / show FAB on scrolling.
        lstAlumnos.addOnScrollListener(new HidingScrollListener() {

            @Override
            public void onHide() {
                // Solo si NO está activo el modo de acción contextual.
                if (mActionMode == null) {
                    hideFloatingViews();
                }
            }

            @Override
            public void onShow() {
                // Solo si NO está activo el modo de acción contextual.
                if (mActionMode == null) {
                    showFloatingViews();
                }
            }
        });
    }

    // Muestra las vistas flotantes.
    private void showFloatingViews() {
        ViewCompat.animate(fabAccion).translationY(0);
    }

    // Oculta las vistas flotantes.
    private void hideFloatingViews() {
        ViewCompat.animate(fabAccion).translationY(fabAccion
                .getHeight() + getResources()
                .getDimensionPixelOffset(R.dimen.fab_margin));
    }

    // Al crear el modo de acción contextual.
    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        mActionMode = actionMode;
        actionMode.getMenuInflater().inflate(R.menu.activity_main_contextual,
                menu);
        // Se ocultan las vistas flotantes.
        hideFloatingViews();
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    // Cuando se pulsa un ítem de menú del modo de acción contextual.
    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.mnuEliminar) {
            mAdaptador.removeSelectedItems();
            actionMode.finish();
        }
        return true;
    }

    // Al finalizar el modo de acción contextual.
    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        mAdaptador.clearSelections();
        this.mActionMode = null;
        // Se muestran las vistas flotantes.
        showFloatingViews();
    }

    // Cuando se hace click en un elemento de la lista.
    @Override
    public void onItemClick(View view, Alumno alumno, int position) {
        if (this.mActionMode != null) {
            toggleSelection(position);
        } else {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.has_pulsado_sobre, mAdaptador.getItem(position).getNombre()),
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Cuando se hace click largo sobre un elemento de la lista.
    @Override
    public void onItemLongClick(View view, Alumno alumno, int position) {
        if (this.mActionMode != null) {
            toggleSelection(position);
        } else {
            toolbar.startActionMode(this);
            toggleSelection(position);
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

    // Agrega un alumno a la lista.
    private void agregarAlumno(Alumno alumno) {
        // Se agrega el alumno.
        mAdaptador.addItem(alumno, mAdaptador.getItemCount());
        lstAlumnos.scrollToPosition(mAdaptador.getItemCount() - 1);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Se salva el estado del RecyclerView.
        mEstadoLista = mLayoutManager.onSaveInstanceState();
        outState.putParcelable(STATE_LISTA, mEstadoLista);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Se obtiene el estado anterior de la lista.
        mEstadoLista = savedInstanceState.getParcelable(STATE_LISTA);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Se retaura el estado de la lista.
        if (mEstadoLista != null) {
            mLayoutManager.onRestoreInstanceState(mEstadoLista);
        }
    }

}
