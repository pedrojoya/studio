package es.iessaladillo.pedrojoya.pr015;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        OnItemClickListener {

    // Vistas.
    private GridView grdCuadricula;

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        grdCuadricula = (GridView) this.findViewById(R.id.grdAlbumes);
        // Se crea el adaptador.
        grdCuadricula.setAdapter(new ConceptosAdapter(this, getDatos()));
        // Establezco el listener para el onClick sobre la cuadrícula.
        grdCuadricula.setOnItemClickListener(this);
    }

    // Construye y retorna el ArrayList de conceptos.
    private ArrayList<Concepto> getDatos() {
        ArrayList<Concepto> conceptos = new ArrayList<>();
        conceptos.add(new Concepto(R.drawable.animal, "Animal", "Animal"));
        conceptos.add(new Concepto(R.drawable.bridge, "Bridge", "Puente"));
        conceptos.add(new Concepto(R.drawable.flag, "Flag", "Bandera"));
        conceptos.add(new Concepto(R.drawable.food, "Food", "Comida"));
        conceptos.add(new Concepto(R.drawable.fruit, "Fruit", "Fruta"));
        conceptos.add(new Concepto(R.drawable.glass, "Glass", "Vaso"));
        conceptos.add(new Concepto(R.drawable.plant, "Plant", "Planta"));
        conceptos.add(new Concepto(R.drawable.science, "Science", "Ciencia"));
        conceptos.add(new Concepto(R.drawable.sea, "Sea", "Mar"));
        conceptos.add(new Concepto(R.drawable.space, "Space", "Espacio"));
        conceptos.add(new Concepto(R.drawable.art, "Art", "Arte"));
        conceptos.add(new Concepto(R.drawable.furniture, "Furniture",
                "Mobiliario"));
        return conceptos;
    }

    // Cuando se hace click en un concepto.
    @Override
    public void onItemClick(AdapterView<?> grd, View v, int position, long id) {
        // Se obtiene el concepto.
        Concepto concepto = (Concepto) grdCuadricula
                .getItemAtPosition(position);
        // Se informa al usuario.
        Toast.makeText(this, concepto.getEnglish(), Toast.LENGTH_SHORT).show();
    }

}