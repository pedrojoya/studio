package es.iessaladillo.pedrojoya.pr107;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.software.shell.fab.ActionButton;

import java.util.List;


public class MainActivity extends AppCompatActivity implements ActionMode.Callback,
        AlumnosAdapter.OnItemClickListener, AlumnosAdapter.OnItemLongClickListener,
        AlumnosAdapter.OnEmptyStateChangedListener {

    private RecyclerView lstAlumnos;
    private ActionButton btnAgregar;
    private AlumnosAdapter mAdaptador;
    private TextView lblNoHayAlumnos;
    private ActionMode mActionMode;
    private SwipeToDismissTouchListener swipeToDismissTouchListener;
    private Toolbar toolbar;
    private DragController mDragController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        initVistas();
    }

    private void initVistas() {
        lblNoHayAlumnos = (TextView) findViewById(R.id.lblNoHayAlumnos);
        lstAlumnos = (RecyclerView) findViewById(R.id.lstAlumnos);
        lstAlumnos.setHasFixedSize(true);
        mAdaptador = new AlumnosAdapter(DB.getAlumnos());
        mAdaptador.setOnItemClickListener(this);
        mAdaptador.setOnItemLongClickListener(this);
        mAdaptador.setOnEmptyStateChangedListener(this);
        lstAlumnos.setAdapter(mAdaptador);
        lstAlumnos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lstAlumnos.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        lstAlumnos.setItemAnimator(new DefaultItemAnimator());
        btnAgregar = (ActionButton) findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarAlumno(DB.getNextAlumno());
            }
        });
        swipeToDismissTouchListener = new SwipeToDismissTouchListener(lstAlumnos, new SwipeToDismissTouchListener.DismissCallbacks() {

            @Override
            public SwipeToDismissTouchListener.SwipeDirection canDismiss(int position) {
                return SwipeToDismissTouchListener.SwipeDirection.RIGHT;
            }

            @Override
            public void onDismiss(RecyclerView view, List<SwipeToDismissTouchListener.PendingDismissData> dismissData) {
                for (SwipeToDismissTouchListener.PendingDismissData data : dismissData) {
                    mAdaptador.removeItem(data.position);
                }
            }

            @Override
            public void onResetMotion() {
            }

            @Override
            public void onTouchDown() {
            }


        });
        lstAlumnos.addOnItemTouchListener(swipeToDismissTouchListener);

        lstAlumnos.addOnScrollListener(new HidingScrollListener() {

            @Override
            public void onHide() {
                btnAgregar.hide();
                toolbar.animate().translationY(-toolbar.getHeight());
            }

            @Override
            public void onShow() {
                btnAgregar.show();
                toolbar.animate().translationY(0);
            }
        });
        // Drag & drop.
        ImageView overlay = (ImageView) findViewById(R.id.overlay);
        mDragController = new DragController(lstAlumnos, overlay) {

            @Override
            void onDragStarted() {
                Log.d("Mia", "onDragStarted");
            }

            @Override
            void onSwapDone() {
                // Si está activo el modo de acción contextual se deshabilita en cuenta se hace
                // un intercambio por drag and drop.
                if (mActionMode != null) {
                    mActionMode.finish();
                }
            }

            @Override
            void onDragEnded() {
            }

        };
        lstAlumnos.addOnItemTouchListener(mDragController);
    }

    private void showFloatingViews() {
        btnAgregar.show();
        toolbar.setVisibility(View.VISIBLE);
    }

    private void hideFloatingViews() {
        btnAgregar.hide();
        toolbar.setVisibility(View.GONE);
    }

    // Al crear el modo de acción contextual.
    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        mActionMode = actionMode;
        actionMode.getMenuInflater().inflate(R.menu.activity_main_contextual, menu);
        // Se ocultan las vistas flotantes.
        hideFloatingViews();
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        swipeToDismissTouchListener.setEnabled(false);
        // this.mActionMode = actionMode;
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
        swipeToDismissTouchListener.setEnabled(true);
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
            Toast.makeText(getApplicationContext(), "Has pulsado sobre " +
                    mAdaptador.getItem(position).getNombre(), Toast.LENGTH_SHORT).show();
        }
    }

    // Cuando se hace click largo sobre un elemento de la lista.
    @Override
    public void onItemLongClick(View view, Alumno alumno, int position) {
        if (this.mActionMode != null) {
            toggleSelection(position);
        } else {
            startSupportActionMode(this);
            toggleSelection(position);
        }
    }

    private void toggleSelection(int position) {
        // Se cambia el estado de selección
        mAdaptador.toggleSelection(position);
        // Se actualiza el texto del action mode contextual.
        mActionMode.setTitle(mAdaptador.getSelectedItemCount() + " / " + mAdaptador.getItemCount());
        // Si ya no hay ningún elemento seleccionado se finaliza el modo de acción contextual
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

    // Cuando la lista para a o deja de estar vacía.
    @Override
    public void onEmptyStateChanged(boolean isEmpty) {
        lblNoHayAlumnos.setVisibility(isEmpty ? View.VISIBLE : View.INVISIBLE);
    }

}
