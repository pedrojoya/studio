package es.iessaldillo.pedrojoya.pr159;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements ConceptosAdapter.OnItemClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configToolbar();
        initVistas();
    }

    private void configToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    private void initVistas() {
        ConceptosAdapter mAdaptador = new ConceptosAdapter(getDatos());
        mAdaptador.setOnItemClickListener(this);
        RecyclerView grdConceptos = (RecyclerView) findViewById(R.id.grdConceptos);
        if (grdConceptos != null) {
            grdConceptos.setHasFixedSize(true);
            // El grid tendrá dos columnas
            grdConceptos.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.gridColumns)));
            grdConceptos.setItemAnimator(new DefaultItemAnimator());
            grdConceptos.setAdapter(mAdaptador);
        }
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
        // Se inicia la actividad de detalle con animación de la foto del concepto seleccionado.
        DetalleActivity.start(this, concepto, view.findViewById(R.id.imgFoto));
    }

}
