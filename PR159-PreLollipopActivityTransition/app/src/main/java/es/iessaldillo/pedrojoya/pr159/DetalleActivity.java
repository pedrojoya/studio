package es.iessaldillo.pedrojoya.pr159;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
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
    private ImageView imgAppbar;
    private ImageView imgFotoDetalle;
    private WebView wvNavegador;
    private AppBarLayout appbar;
    private boolean mCambioOrientacion;
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
        realizarAnimacion(savedInstanceState);
    }

    // Configura la Toolbar.
    private void configToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    // Inicializa las vistas.
    private void initVistas() {
        imgFotoDetalle = (ImageView) findViewById(R.id.imgFotoDetalle);
        imgAppbar = (ImageView) findViewById(R.id.imgAppbar);
        wvNavegador = (WebView) findViewById(R.id.wvNavegador);
        // Por defecto se expande la appbar.
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        appbar.setVisibility(View.INVISIBLE);
        appbar.setExpanded(true);
        if (mConcepto != null) {
            mostrarConcepto();
        }
    }

    // Carga los datos del concepto en las vistas correspondientes.
    private void mostrarConcepto() {
        imgFotoDetalle.setImageResource(mConcepto.getFotoResId());
        // imgAppbar sólo está disponible en orientación portrait.
        if (imgAppbar != null) {
            imgAppbar.setImageResource(mConcepto.getFotoResId());
        }
        setTitle(mConcepto.getEnglish());
        wvNavegador.loadUrl("http://www.thefreedictionary.com/" + mConcepto.getEnglish());
    }

    // Realiza la animación y adapta la visibilidad de las vistas.
    private void realizarAnimacion(Bundle savedInstanceState) {
        mExitTransition = ActivityTransition.with(getIntent()).to(imgFotoDetalle).duration(
                getResources().getInteger(R.integer.duracion)).start(savedInstanceState);
        appbar.postDelayed(() -> {
            appbar.setVisibility(View.VISIBLE);
            if (getResources().getConfiguration().orientation
                    == Configuration.ORIENTATION_PORTRAIT) {
                imgFotoDetalle.setVisibility(View.INVISIBLE);
            }
        }, getResources().getInteger(R.integer.duracion));
        // Es necesario saber si venimos de un cambio de orientación para NO realizar la
        // animación de salida.
        mCambioOrientacion = savedInstanceState != null;
    }

    // Inicia la actividad. Recibe la vista a animar.
    public static void start(Activity activity, Concepto concepto, View view) {
        Intent intent = new Intent(activity, DetalleActivity.class);
        intent.putExtra(EXTRA_CONCEPTO, concepto);
        ActivityTransitionLauncher.with(activity).from(view).launch(intent);
    }

    @Override
    public void onBackPressed() {
        CardView cvTarjeta = (CardView) findViewById(R.id.cvTarjeta);
        if (cvTarjeta != null) {
            cvTarjeta.animate().translationY(cvTarjeta.getHeight()).setDuration(3000);
        }
        imgFotoDetalle.setVisibility(View.VISIBLE);
        appbar.setVisibility(View.INVISIBLE);
        // Sólo se realiza la transición de salida a la inversa que la de entrada si no ha habido
        // un cambio de orientación.
        if (!mCambioOrientacion) {
            mExitTransition.exit(this);
        } else {
            finish();
        }
    }

}
