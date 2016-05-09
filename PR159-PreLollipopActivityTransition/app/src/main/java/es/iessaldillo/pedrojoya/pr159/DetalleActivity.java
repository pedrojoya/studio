package es.iessaldillo.pedrojoya.pr159;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.kogitune.activity_transition.ExitActivityTransition;

public class DetalleActivity extends AppCompatActivity {

    private static final String EXTRA_CONCEPTO = "extra_concepto";

    private Concepto mConcepto;
    private ImageView imgFotoDetalle;
    private ExitActivityTransition mExitTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        if (getIntent() != null && getIntent().hasExtra(EXTRA_CONCEPTO)) {
            mConcepto = getIntent().getParcelableExtra(EXTRA_CONCEPTO);
        }
        configToolbar();
        initVistas();
        // Se realiza la animación.
        mExitTransition = ActivityTransition.with(getIntent()).to(imgFotoDetalle)
                .duration(getResources().getInteger(R.integer.duracion)).start(savedInstanceState);
    }

    // Configura la Toolbar.
    private void configToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    // Inicializa las vistas.
    private void initVistas() {
        imgFotoDetalle = (ImageView) findViewById(R.id.imgFotoDetalle);
        WebView wvNavegador = (WebView) findViewById(R.id.wvNavegador);
        if (mConcepto != null && wvNavegador != null) {
            imgFotoDetalle.setImageResource(mConcepto.getFotoResId());
            setTitle(mConcepto.getEnglish());
            wvNavegador.loadUrl("http://www.thefreedictionary.com/" + mConcepto.getEnglish());
            // Por defecto se expande la appbar.
            AppBarLayout appbar = (AppBarLayout) findViewById(R.id.appbar);
            if (appbar != null) {
                appbar.setExpanded(true);
            }
        }
    }

    // Inicia la actividad. Recibe la vista a animar.
    public static void start(Activity activity, Concepto concepto, View view) {
        Intent intent = new Intent(activity, DetalleActivity.class);
        intent.putExtra(EXTRA_CONCEPTO, concepto);
        ActivityTransitionLauncher.with(activity).from(view).launch(intent);
    }

    @Override
    public void onBackPressed() {
        // Al pulsar atrás se inicia la transición a la inversa.
        CardView cvTarjeta = (CardView) findViewById(R.id.cvTarjeta);
        if (cvTarjeta != null) {
            cvTarjeta.animate().translationY(cvTarjeta.getHeight()).setDuration(1000);
        }
        mExitTransition.exit(this);
    }

}
