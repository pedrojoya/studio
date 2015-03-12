package pedrojoya.iessaladillo.es.pr106;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements View.OnClickListener,
        AlumnosAdapter.OnItemClickListener, AlumnosAdapter.OnItemLongClickListener {

    private RecyclerView lstAlumnos;
    private ImageButton btnAgregar;
    private TextView lblNumAlumnos;

    private AlumnosAdapter mAdaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(this, R.style.TextAppearance_AppCompat_Title);
        setSupportActionBar(toolbar);
        initVistas();
    }

    private void initVistas() {
        lstAlumnos = (RecyclerView) findViewById(R.id.lstAlumnos);
        lstAlumnos.setHasFixedSize(true);
        mAdaptador = new AlumnosAdapter(this, DB.getAlumnos());
        mAdaptador.setEmptyView((TextView) findViewById(R.id.lblNoHayAlumnos));
        mAdaptador.setOnItemClickListener(this);
        mAdaptador.setOnItemLongClickListener(this);
        lstAlumnos.setAdapter(mAdaptador);
        lstAlumnos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lstAlumnos.setItemAnimator(new DefaultItemAnimator());
        btnAgregar = (ImageButton) findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(this);
        lblNumAlumnos = (TextView) findViewById(R.id.lblNumAlumnos);
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
        mAdaptador.addItem(new Alumno(nombre));
        lstAlumnos.scrollToPosition(mAdaptador.getItemCount() - 1);
    }

    // Cuando se hace click sobre un elemento de la lista.
    @Override
    public void onItemClick(View view, Alumno alumno, int position) {
        Toast.makeText(this, getString(R.string.ha_pulsado_sobre) + alumno.getNombre(),
                Toast.LENGTH_SHORT).show();
    }

    // Cuando se hace long click sobre un elemento de la lista.
    @Override
    public void onItemLongClick(View view, Alumno alumno, int position) {
        // Se elimina el alumno.
        mAdaptador.removeItem(position);
    }
}
