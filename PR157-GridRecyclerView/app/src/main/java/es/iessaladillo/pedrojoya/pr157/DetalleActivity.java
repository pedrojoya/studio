package es.iessaladillo.pedrojoya.pr157;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

public class DetalleActivity extends AppCompatActivity {

    private static final String EXTRA_CONCEPTO = "extra_concepto";

    private Concepto mConcepto;
    private ImageView imgFotoDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        configTransition();
        if (getIntent() != null && getIntent().hasExtra(EXTRA_CONCEPTO)) {
            mConcepto = getIntent().getParcelableExtra(EXTRA_CONCEPTO);
        }
        configToolbar();
        initVistas();
    }

    // Configura la Toolbar.
    private void configToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    // Configura la transición de entrada y retorno.
    private void configTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
//            transition.setDuration(1000);
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            transition.excludeTarget(android.R.id.navigationBarBackground, true);
            // Si no se excluye el CardView da un error (parece un bug).
            //transition.excludeTarget(R.id.cvTarjeta, true);
            transition.excludeTarget(R.id.appbar, true);
            transition.excludeTarget(R.id.toolbar, true);
            transition.excludeTarget(R.id.collapsingToolbar, true);
            getWindow().setEnterTransition(transition);
//            Slide retorno = new Slide(Gravity.RIGHT);
//            getWindow().setReturnTransition(transition);
        }
    }

    // Inicializa las vistas.
    private void initVistas() {
        imgFotoDetalle = (ImageView) findViewById(R.id.imgFotoDetalle);
        WebView wvNavegador = (WebView) findViewById(R.id.wvNavegador);
        if (mConcepto != null) {
            imgFotoDetalle.setImageResource(mConcepto.getFotoResId());
            setTitle(mConcepto.getEnglish());
            wvNavegador.loadUrl("http://www.thefreedictionary.com/" + mConcepto.getEnglish());
            // Por defecto se expande la appbar.
            AppBarLayout appbar = (AppBarLayout) findViewById(R.id.appbar);
            appbar.setExpanded(true);
        }
    }

    // Inicia la actividad.
    public static void start(Activity activity, Concepto concepto, View vFotoCompartida)  {
        Intent intent = new Intent(activity, DetalleActivity.class);
        intent.putExtra(EXTRA_CONCEPTO, concepto);


        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(
                        activity,
                        vFotoCompartida,
                        ViewCompat.getTransitionName(vFotoCompartida)
                );
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setTitle("");
        ActivityCompat.finishAfterTransition(this);
    }
}
