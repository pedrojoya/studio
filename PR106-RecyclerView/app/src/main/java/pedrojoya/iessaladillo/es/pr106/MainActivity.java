package pedrojoya.iessaladillo.es.pr106;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements AlumnosAdapter.OnItemClickListener,
        AlumnosAdapter.OnItemLongClickListener {

    private RecyclerView lstAlumnos;
    private ImageButton btnAgregar;

    private AlumnosAdapter mAdaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Para que al cambiar la orientación no pierda el tamaño.
        toolbar.setTitleTextAppearance(this, R.style.TextAppearance_AppCompat_Title);
        setSupportActionBar(toolbar);
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        lstAlumnos = (RecyclerView) findViewById(R.id.lstAlumnos);
        lstAlumnos.setHasFixedSize(true);
        mAdaptador = new AlumnosAdapter(this, DB.getAlumnos());
        mAdaptador.setEmptyView(findViewById(R.id.lblNoHayAlumnos));
        mAdaptador.setOnItemClickListener(this);
        mAdaptador.setOnItemLongClickListener(this);
        lstAlumnos.setAdapter(mAdaptador);
        lstAlumnos.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lstAlumnos.setItemAnimator(new DefaultItemAnimator());
        btnAgregar = (ImageButton) findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarAlumno("Alumno " + DB.getNext());
            }
        });

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
