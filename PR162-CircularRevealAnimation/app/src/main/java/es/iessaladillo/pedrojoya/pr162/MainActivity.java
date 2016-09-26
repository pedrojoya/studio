package es.iessaladillo.pedrojoya.pr162;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final long DURACION_ANIM_MS = 1000;
    private static final String STATE_PANEL_VISIBLE = "state_panel_visible";
    private LinearLayout llPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        initVistas();
        restaurarVisibilidadPanel(savedInstanceState);
    }

    // Restaura el estado de visibilidad del panel en base al estado anterior
    // antes del cambio de configuración.
    private void restaurarVisibilidadPanel(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.getBoolean(STATE_PANEL_VISIBLE)) {
            llPanel.setVisibility(View.VISIBLE);
        } else {
            llPanel.setVisibility(View.INVISIBLE);
        }
    }

    private void initVistas() {
        llPanel = (LinearLayout) findViewById(R.id.llPanel);
        ImageView imgGaleria = (ImageView) findViewById(R.id.imgGaleria);
        if (imgGaleria != null) {
            imgGaleria.setOnClickListener(view -> {
                Toast.makeText(getApplicationContext(), R.string.galeria, Toast.LENGTH_SHORT)
                        .show();
                revelar(llPanel, DURACION_ANIM_MS, true);
            });
        }
        ImageView imgVideo = (ImageView) findViewById(R.id.imgVideo);
        if (imgVideo != null) {
            imgVideo.setOnClickListener(view -> {
                Toast.makeText(getApplicationContext(), R.string.video, Toast.LENGTH_SHORT).show();
                revelar(llPanel, DURACION_ANIM_MS, true);
            });
        }
        ImageView imgFoto = (ImageView) findViewById(R.id.imgFoto);
        if (imgFoto != null) {
            imgFoto.setOnClickListener(view -> {
                Toast.makeText(getApplicationContext(), R.string.foto, Toast.LENGTH_SHORT).show();
                revelar(llPanel, DURACION_ANIM_MS, true);
            });
        }
    }

    private void setupToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuAdjuntar:
                // Se conmuta el estado de visualización del panel.
                togglePanel();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Conmuta el estado de visualización del panel.
    private void togglePanel() {
        if (llPanel.getVisibility() == View.INVISIBLE) {
            revelar(llPanel, DURACION_ANIM_MS, false);
        } else {
            revelar(llPanel, DURACION_ANIM_MS, true);
        }
    }

    // Realiza una animación de revelación circular. Recibe la vista, la
    // duración y si se trata del modo inverso (ocultar).
    @SuppressWarnings("SameParameterValue")
    private void revelar(final View vista, long duracion, boolean reverse) {
        // El origen del círculo de revelación será la esquina superior derecha
        // del panel.
        int origenX = vista.getRight();
        int origenY = vista.getTop();
        int radio = Math.max(vista.getWidth(), vista.getHeight());
        Animator anim;
        if (!reverse) {
            anim = ViewAnimationUtils.createCircularReveal(vista, origenX, origenY, 0, radio);
            // La vista se hace visible antes de realizar la animación.
            vista.setVisibility(View.VISIBLE);
        } else {
            // Al ser el modo inverso, el radio final será 0.
            anim = ViewAnimationUtils.createCircularReveal(vista, origenX, origenY, radio, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    // Cuando termina la animación se oculta la vista.
                    vista.setVisibility(View.INVISIBLE);
                }
            });
        }
        anim.setDuration(duracion);
        anim.start();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Se guarda el estado de visualización del panel antes de cambiar
        // de configuración.
        outState.putBoolean(STATE_PANEL_VISIBLE, llPanel.getVisibility() == View.VISIBLE);
        super.onSaveInstanceState(outState);
    }

}
