package pedrojoya.iessaladillo.es.pr105;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;


public class DetalleActivity extends ActionBarActivity {

    private NestedScrollView nsvScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        initVistas();
    }

    private void initVistas() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getTitle());
        nsvScroll = (NestedScrollView) findViewById(R.id.nsvScroll);
        FloatingActionButton fabAccion = (FloatingActionButton) findViewById(R.id.fabAccion);
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
