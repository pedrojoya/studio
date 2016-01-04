package es.iessaladillo.pedrojoya.pr164;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements AlumnosAdapter.OnItemClickListener {

    private static final String STATE_DATOS = "state_datos";

    private AlumnosAdapter mAdaptador;
    private ArrayList<ListItem> mDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            mDatos = getDatos();
        }
        else {
            mDatos = savedInstanceState.getParcelableArrayList(STATE_DATOS);
        }
        setupToolbar();
        setupRecyclerView();
    }

    private void setupToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    private void setupRecyclerView() {
        RecyclerView lstAlumnos = (RecyclerView) findViewById(R.id.lstAlumnos);
        lstAlumnos.setHasFixedSize(true);
        mAdaptador = new AlumnosAdapter(mDatos);
        mAdaptador.setOnItemClickListener(this);
        lstAlumnos.setAdapter(mAdaptador);
        lstAlumnos.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        lstAlumnos.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_DATOS, mAdaptador.getData());
    }

    private ArrayList<ListItem> getDatos() {
        ArrayList<ListItem> datos = new ArrayList<>();
        // Primer grupo.
        datos.add(new Grupo("CFGM Sistemas Microinformáticos y Redes"));
        datos.add(new Alumno("Baldomero", 16, "CFGM", "2º"));
        datos.add(new Alumno("Sergio", 27, "CFGM", "1º"));
        datos.add(new Alumno("Atanasio", 17, "CFGM", "1º"));
        datos.add(new Alumno("Oswaldo", 26, "CFGM", "1º"));
        datos.add(new Alumno("Rodrigo", 22, "CFGM", "2º"));
        datos.add(new Alumno("Antonio", 16, "CFGM", "1º"));
        // Segundo grupo.
        datos.add(new Grupo("CFGS Desarrollo de Aplicaciones Multiplataforma"));
        datos.add(new Alumno("Pedro", 22, "CFGS", "2º"));
        datos.add(new Alumno("Pablo", 22, "CFGS", "2º"));
        datos.add(new Alumno("Rodolfo", 21, "CFGS", "1º"));
        datos.add(new Alumno("Gervasio", 24, "CFGS", "2º"));
        datos.add(new Alumno("Prudencia", 20, "CFGS", "2º"));
        datos.add(new Alumno("Gumersindo", 17, "CFGS", "2º"));
        datos.add(new Alumno("Gerardo", 18, "CFGS", "1º"));
        datos.add(new Alumno("Óscar", 21, "CFGS", "2º"));
        return datos;
    }

    @Override
    public void onItemClick(View view, Alumno alumno, int position) {
        Toast.makeText(this, getString(R.string.datos_alumno,
                alumno.getNombre(), alumno.getCurso(), alumno.getCiclo()),
                Toast.LENGTH_SHORT).show();
    }

}
