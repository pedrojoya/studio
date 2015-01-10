package pedrojoya.iessaladillo.es.pr106;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private RecyclerView lstAlumnos;
    private ImageButton btnAgregar;
    private AlumnosAdapter adaptador;
    private TextView lblNumAlumnos;

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
        adaptador.setEmptyView((TextView) findViewById(R.id.lblNoHayAlumnos));
        lstAlumnos.setAdapter(adaptador);
        lstAlumnos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        lstAlumnos.setItemAnimator(new DefaultItemAnimator());
        lstAlumnos.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        btnAgregar = (ImageButton) findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(this);
        lblNumAlumnos = (TextView) findViewById(R.id.lblNumAlumnos);
        lstAlumnos.addOnItemTouchListener(new DragDropTouchListener(lstAlumnos, this) {
            @Override
            protected void onItemSwitch(RecyclerView recyclerView, int from, int to) {
                adaptador.swapItems(from, to);
                adaptador.notifyItemChanged(from);
                adaptador.notifyItemChanged(to);
            }

            @Override
            protected void onItemDrop(RecyclerView recyclerView, int position) {

            }
        });
//        lstAlumnos.addOnItemTouchListener(new SwipeToDismissTouchListener(lstAlumnos, new SwipeToDismissTouchListener.DismissCallbacks() {
//            @Override
//            public SwipeToDismissTouchListener.SwipeDirection canDismiss(int position) {
//                return SwipeToDismissTouchListener.SwipeDirection.RIGHT;
//            }
//
//            @Override
//            public void onDismiss(RecyclerView view, List<SwipeToDismissTouchListener.PendingDismissData> dismissData) {
//                for (SwipeToDismissTouchListener.PendingDismissData data : dismissData) {
//                    adaptador.removeItem(data.position);
//                    adaptador.notifyItemRemoved(data.position);
//                }
//            }
//        }));
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
                agregarAlumno("Alumno " + DB.getNext());
                break;
        }
    }

    // Agrega un alumno a la lista.
    private void agregarAlumno(String nombre) {
        // Se agrega el alumno.
        adaptador.addItem(new Alumno(nombre));
        lstAlumnos.scrollToPosition(adaptador.getItemCount() - 1);
    }

}
