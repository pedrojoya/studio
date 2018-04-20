package es.iessaladillo.pedrojoya.pr135;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;


public class MainActivity extends AppCompatActivity {

    private UltimateRecyclerView lstAlumnos;

    private AlumnosAdapter mAdaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        lstAlumnos = ActivityCompat.requireViewById(this, R.id.lstAlumnos);
        lstAlumnos.setHasFixedSize(true);
        mAdaptador = new AlumnosAdapter(this, DB.getAlumnos());
        lstAlumnos.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lstAlumnos.setItemAnimator(new DefaultItemAnimator());
    }

}
