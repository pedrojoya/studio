package es.iessaladillo.pedrojoya.pr012;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements OnItemClickListener {

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        getVistas();
    }

    // Obtiene e inicializa las vistas.
    private void getVistas() {
        ListView lstAlumnos = (ListView) this.findViewById(R.id.lstAlumnos);
        lstAlumnos.setEmptyView(findViewById(R.id.lblEmpty));
        // Se crea el adaptador y se asigna a la lista.
        lstAlumnos.setAdapter(new AlumnosAdapter(this, getDatos()));
        // La actividad actuará como listener cuando se pulse un elemento.
        lstAlumnos.setOnItemClickListener(this);
    }

    // Al pulsar sobre un elemento de la lista.
    @Override
    public void onItemClick(AdapterView<?> lst, View v, int position, long id) {
        // Se muestra el alumno sobre el que se ha pulsado.
        Alumno alumno = (Alumno) lst.getItemAtPosition(position);
        Toast.makeText(this, alumno.getNombre(), Toast.LENGTH_SHORT).show();
    }

    // Crea el ArrayList de datos.
    private ArrayList<Alumno> getDatos() {
        ArrayList<Alumno> alumnos = new ArrayList<>();
        alumnos.add(new Alumno(R.drawable.foto1,
                "Dolores Fuertes de Barriga", 22, "CFGS DAM", "2º", false));
        alumnos.add(new Alumno(R.drawable.foto2, "Baldomero LLégate Ligero", 17,
                "CFGM SMR", "2º", true));
        alumnos.add(new Alumno(R.drawable.foto3, "Jorge Javier Jiménez Jaén", 36,
                "CFGM SMR", "1º", false));
        alumnos.add(new Alumno(R.drawable.foto4, "Fabián Gonzáles Palomino", 67,
                "CFGS DAM", "1º", false));
        return alumnos;
    }

}
