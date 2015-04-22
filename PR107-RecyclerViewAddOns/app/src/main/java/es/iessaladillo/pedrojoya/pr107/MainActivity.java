package es.iessaladillo.pedrojoya.pr107;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.software.shell.fab.ActionButton;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, ItemTouchListenerAdapter.RecyclerViewOnItemClickListener, ActionMode.Callback {

    private RecyclerView lstAlumnos;
    private ActionButton btnAgregar;
    private AlumnosAdapter adaptador;
    private TextView lblNumAlumnos;
    private ActionMode actionMode;
    private DragDropTouchListener dragDropTouchListener;
    private SwipeToDismissTouchListener swipeToDismissTouchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getVistas();
    }

    private void getVistas() {
        lstAlumnos = (RecyclerView) findViewById(R.id.lstAlumnos);
        lstAlumnos.setHasFixedSize(true);
        adaptador = new AlumnosAdapter(this, DB.getAlumnos());
        adaptador.setEmptyView(findViewById(R.id.lblNoHayAlumnos));
        lstAlumnos.setAdapter(adaptador);
        lstAlumnos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lstAlumnos.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        btnAgregar = (ActionButton) findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(this);
        lblNumAlumnos = (TextView) findViewById(R.id.lblNumAlumnos);
        lstAlumnos.addOnItemTouchListener(new ItemTouchListenerAdapter(lstAlumnos, this));
        dragDropTouchListener = new DragDropTouchListener(lstAlumnos, this) {
            @Override
            protected void onItemSwitch(RecyclerView recyclerView, int from, int to) {
                adaptador.swapPositions(from, to);
                adaptador.clearSelection(from);
                adaptador.notifyItemChanged(to);
                if (actionMode != null) actionMode.finish();
            }

            @Override
            protected void onItemDrop(RecyclerView recyclerView, int position) {
            }
        };
        lstAlumnos.addOnItemTouchListener(dragDropTouchListener);
        swipeToDismissTouchListener = new SwipeToDismissTouchListener(lstAlumnos, new SwipeToDismissTouchListener.DismissCallbacks() {

            @Override
            public SwipeToDismissTouchListener.SwipeDirection canDismiss(int position) {
                return SwipeToDismissTouchListener.SwipeDirection.RIGHT;
            }

            @Override
            public void onDismiss(RecyclerView view, List<SwipeToDismissTouchListener.PendingDismissData> dismissData) {
                for (SwipeToDismissTouchListener.PendingDismissData data : dismissData) {
                    adaptador.removeItem(data.position);
                }
            }
        });
        lstAlumnos.addOnItemTouchListener(swipeToDismissTouchListener);
        lstAlumnos.setOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                btnAgregar.hide();
            }

            @Override
            public void onShow() {
                btnAgregar.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // btnAgregar activo o no dependiendo de si hay nombre.
    private void checkDatos(String nombre) {
        btnAgregar.setEnabled(!TextUtils.isEmpty(nombre));
    }

    // Al hacer click sobre el botón.
    @Override
    public void onClick(View v) {
        // Dependiendo de la vista pulsada.
        switch (v.getId()) {
            case R.id.btnAgregar:
                int id = DB.getNext();
                agregarAlumno(id, "Alumno " + id);
                break;
        }
    }

    // Agrega un alumno a la lista.
    private void agregarAlumno(int id, String nombre) {
        // Se agrega el alumno.
        adaptador.addItem(new Alumno(id, nombre));
        lstAlumnos.scrollToPosition(adaptador.getItemCount() - 1);
    }

    // Cuando se hace click en un elemento de la lista.
    @Override
    public void onItemClick(RecyclerView parent, View clickedView, int position) {
        if (this.actionMode != null) {
            toggleSelection(position);
        } else {
            Toast.makeText(getApplicationContext(), "Has pulsado sobre " + adaptador.getItem(position).getNombre(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemLongClick(RecyclerView parent, View clickedView, int position) {
        if (this.actionMode != null) {
            toggleSelection(position);
        } else {
            startSupportActionMode(this);
            toggleSelection(position);
            dragDropTouchListener.startDrag();
        }
    }

    private void toggleSelection(int position) {
        adaptador.toggleSelection(position);
        actionMode.setTitle(adaptador.getSelectedItemCount() + " de " + adaptador.getItemCount());
        if (adaptador.getSelectedItemCount() == 0) {
            actionMode.finish();
        }
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        actionMode.getMenuInflater().inflate(R.menu.activity_main_contextual, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        swipeToDismissTouchListener.setEnabled(false);
        this.actionMode = actionMode;
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.mnuEliminar) {
            adaptador.removeSelectedItems();
            actionMode.finish();
        }
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        swipeToDismissTouchListener.setEnabled(true);
        adaptador.clearSelections();
        this.actionMode = null;
    }

}
