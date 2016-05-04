package pedrojoya.iessaladillo.es.pr105.actividades;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import pedrojoya.iessaladillo.es.pr105.R;


public class DetalleActivity extends AppCompatActivity {

    private NestedScrollView nsvScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        // Se muestra el título en la collapsing toolbar.
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        if (collapsingToolbarLayout != null) {
            collapsingToolbarLayout.setTitle(getTitle());
        }
        nsvScroll = (NestedScrollView) findViewById(R.id.nsvScroll);
        FloatingActionButton fabAccion = (FloatingActionButton) findViewById(R.id.fabAccion);
        if (fabAccion != null) {
            fabAccion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(nsvScroll, "Quillo que", Snackbar.LENGTH_SHORT).setAction("Deshacer", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(), "Quieres deshacer", Toast.LENGTH_SHORT).show();
                        }
                    }).show();
                }
            });
        }
    }


}
