package es.iessaladillo.pedrojoya.pr014;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_LISTA = "STATE_LISTA";
    private static final String STATE_DATA = "STATE_DATA";

    private ListView lstAlumnos;
    private ArrayList<Alumno> mData;
    private Parcelable mEstadoLista;
    private AlumnosAdapter mAdaptador;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mData = savedInstanceState
                        == null ? getDataInicial() : savedInstanceState
                        .<Alumno>getParcelableArrayList(
                STATE_DATA);
        initVistas();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Se salva el estado del ListView.
        mEstadoLista = lstAlumnos.onSaveInstanceState();
        outState.putParcelable(STATE_LISTA, mEstadoLista);
        outState.putParcelableArrayList(STATE_DATA, mAdaptador.getData());
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
            lstAlumnos.onRestoreInstanceState(mEstadoLista);
        }
    }

    private void initVistas() {
        lstAlumnos = (ListView) findViewById(R.id.lstAlumnos);
        lstAlumnos.setEmptyView(findViewById(R.id.emptyView));
        mAdaptador = new AlumnosAdapter(this, mData);
        lstAlumnos.setAdapter(mAdaptador);
        lstAlumnos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Alumno alumno = (Alumno) lstAlumnos.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        getString(R.string.se_ha_pulsado, alumno.getNombre()), Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private ArrayList<Alumno> getDataInicial() {
        return new ArrayList<>(Arrays.asList(new Alumno("Pedro", 22, "CFGS", "2"),
                new Alumno("Baldomero", 16, "CFGM", "2º"), new Alumno("Sergio", 27, "CFGM", "1º"),
                new Alumno("Pablo", 22, "CFGS", "2º"), new Alumno("Rodolfo", 21, "CFGS", "1º"),
                new Alumno("Atanasio", 17, "CFGM", "1º"), new Alumno("Gervasio", 24, "CFGS", "2º"),
                new Alumno("Prudencia", 20, "CFGS", "2º"), new Alumno("Oswaldo", 26, "CFGM", "1º"),
                new Alumno("Gumersindo", 17, "CFGS", "2º"), new Alumno("Gerardo", 18, "CFGS", "1º"),
                new Alumno("Rodrigo", 22, "CFGM", "2º"), new Alumno("Óscar", 21, "CFGS", "2º"),
                new Alumno("Antonio", 16, "CFGM", "1º")));
    }

}
