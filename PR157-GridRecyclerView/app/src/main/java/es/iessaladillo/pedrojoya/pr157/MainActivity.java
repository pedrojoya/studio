package es.iessaladillo.pedrojoya.pr157;

import android.app.ActivityOptions;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements ConceptosAdapter.OnItemClickListener {


    private RecyclerView grdConceptos;
    private ConceptosAdapter mAdaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
    }

    private void initVistas() {
        grdConceptos = (RecyclerView) findViewById(R.id.grdConceptos);
        grdConceptos.setHasFixedSize(true);
        // El grid tendrá dos columnas
        grdConceptos.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.gridColumns)));
        grdConceptos.setItemAnimator(new DefaultItemAnimator());
        mAdaptador = new ConceptosAdapter(getDatos());
        mAdaptador.setOnItemClickListener(this);
        grdConceptos.setAdapter(mAdaptador);
    }

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

    @Override
    public void onItemClick(View view, Concepto concepto, int position) {
        // Se crea el Bundle de opciones para la transición de actividades
        // con la foto como elemento compartido.
        View vCompartida = view.findViewById(R.id.imgFoto);
        ActivityOptions options = ActivityOptions
                .makeSceneTransitionAnimation(MainActivity.this, vCompartida,
                        vCompartida.getTransitionName());

        // TODO: Que el texto también sea un elemento compartido en la transición.

        // Se inicia la actividad de detalle.
        DetalleActivity.start(this, concepto, options.toBundle());
    }

}
