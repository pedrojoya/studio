package es.iessaladillo.pedrojoya.pr101;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_MAIN_FRAGMENT = "MainFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
        if (getSupportFragmentManager().findFragmentByTag(TAG_MAIN_FRAGMENT) == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.flContenido, MainFragment.newInstance(), TAG_MAIN_FRAGMENT)
                    .commit();
        }
    }

    private void initVistas() {
        setupToolbar();
        setupFAB();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    private void setupFAB() {
        FloatingActionButton btnExportar = (FloatingActionButton) findViewById(R.id.btnExportar);
        if (btnExportar != null) {
            btnExportar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    exportar();
                }
            });
        }
    }

    private void exportar() {
        Fragment frg = getSupportFragmentManager().findFragmentByTag(TAG_MAIN_FRAGMENT);
        if (frg instanceof MainFragment) {
            ((MainFragment) frg).exportar();
        }
    }

}
