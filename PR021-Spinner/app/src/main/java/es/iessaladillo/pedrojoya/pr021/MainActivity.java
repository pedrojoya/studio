package es.iessaladillo.pedrojoya.pr021;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        OnClickListener,
        OnItemSelectedListener {

    private Spinner spnPais;
    private Button btnMostrar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        btnMostrar = (Button) findViewById(R.id.btnMostrar);
        btnMostrar.setOnClickListener(this);
        spnPais = (Spinner) findViewById(R.id.spnPais);
        // Se crea el adaptador y se le asigna al spinner.
        PaisesAdapter adaptador = new PaisesAdapter(this, getPaises());
        spnPais.setAdapter(adaptador);
        // La actividad acutará como listener cuando se seleccione un elemento.
        spnPais.setOnItemSelectedListener(this);
    }

    // Obtiene el ArrayList de paises.
    private ArrayList<Pais> getPaises() {
        ArrayList<Pais> paises = new ArrayList<>();
        paises.add(new Pais(R.drawable.no_flag,
                getString(R.string.elija_un_pais)));
        paises.add(new Pais(R.drawable.de, "Alemania"));
        paises.add(new Pais(R.drawable.dk, "Dinamarca"));
        paises.add(new Pais(R.drawable.es, "España"));
        paises.add(new Pais(R.drawable.fi, "Finlandia"));
        paises.add(new Pais(R.drawable.fr, "Francia"));
        paises.add(new Pais(R.drawable.gr, "Grecia"));
        paises.add(new Pais(R.drawable.nl, "Holanda"));
        paises.add(new Pais(R.drawable.ie, "Irlanda"));
        paises.add(new Pais(R.drawable.is, "Islandia"));
        paises.add(new Pais(R.drawable.it, "Italia"));
        paises.add(new Pais(R.drawable.lt, "Lituania"));
        paises.add(new Pais(R.drawable.no, "Noruega"));
        paises.add(new Pais(R.drawable.pl, "Polonia"));
        paises.add(new Pais(R.drawable.pt, "Portugal"));
        return paises;
    }

    // Al pulsar btnMostrar.
    @Override
    public void onClick(View v) {
        // Se muestra el nombre del país seleccionado.
        Pais pais = (Pais) spnPais.getSelectedItem();
        if (!pais.getNombre().equals(getString(R.string.elija_un_pais))) {
            Toast.makeText(this, pais.getNombre(), Toast.LENGTH_SHORT).show();
        }
    }

    // Al seleccionar un elemento.
    @Override
    public void onItemSelected(AdapterView<?> spn, View v, int position, long id) {
        // Se activa o desactiva el botón dependiendo de la selección.
        btnMostrar.setEnabled(spnPais.getSelectedItemPosition() != 0);
    }

    // Obigatorio en interfaz OnItemSelectedListener.
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

}