package es.iessaladillo.pedrojoya.pr086;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cargarLista();
    }

    private void cargarLista() {
        ArrayList<Alumno> alumnos = new ArrayList<>();
        alumnos.add(new Alumno("Baldomero", "La casa de Baldomero",
                "956956956", "2º CFGS DAM"));
        alumnos.add(new Alumno("Germán Ginés", "La casa de Germán",
                "678678678", "1º CFGS DAM"));
        AlumnosAdapter adaptador = new AlumnosAdapter(this, alumnos);
        ListView lstAlumnos = (ListView) findViewById(R.id.lstAlumnos);
        if (lstAlumnos != null) {
            lstAlumnos.setAdapter(adaptador);
        }
    }

}
