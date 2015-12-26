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

    private void restaurarVisibilidadPanel(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.getBoolean(STATE_PANEL_VISIBLE)) {
            llPanel.setVisibility(View.VISIBLE);
        }
        else {
            llPanel.setVisibility(View.INVISIBLE);
        }
    }

    private void initVistas() {
        llPanel = (LinearLayout) findViewById(R.id.llPanel);
        findViewById(R.id.imgGaleria).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), R.string.galeria, Toast.LENGTH_SHORT).show();
                hidePanel();
            }
        });
        findViewById(R.id.imgVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), R.string.video, Toast.LENGTH_SHORT).show();
                hidePanel();
            }
        });
        findViewById(R.id.imgFoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), R.string.foto, Toast.LENGTH_SHORT).show();
                hidePanel();
            }
        });
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
                togglePanel();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void togglePanel() {
        if (llPanel.getVisibility() == View.INVISIBLE) {
            showPanel();
        } else {
            hidePanel();
        }
    }

    private void hidePanel() {
        revelar(llPanel, true);
    }

    private void showPanel() {
        revelar(llPanel, false);
    }

    private void revelar(final View vista, boolean reverse) {
        int origenX = vista.getRight();
        int origenY = vista.getTop();
        int radio = Math.max(vista.getWidth(), vista.getHeight());
        Animator anim;
        if (!reverse) {
            anim = ViewAnimationUtils.createCircularReveal(vista, origenX,
                    origenY, 0, radio);
            vista.setVisibility(View.VISIBLE);
        } else {
            anim =
                    ViewAnimationUtils.createCircularReveal(vista, origenX,
                            origenY, radio, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    vista.setVisibility(View.INVISIBLE);
                }
            });
        }
        anim.setDuration(DURACION_ANIM_MS);
        anim.start();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_PANEL_VISIBLE, llPanel.getVisibility() == View.VISIBLE);
        super.onSaveInstanceState(outState);
    }
}
