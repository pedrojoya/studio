package es.iessaladillo.pedrojoya.pr136;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;


public class MainActivity extends ActionBarActivity {

    private ImageView imgFoto;
    private ScrollView svScroll;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        imgFoto = (ImageView) findViewById(R.id.imgFoto);
        svScroll = (ScrollView) findViewById(R.id.svScroll);
        // Cuando se hace scroll se realiza el efecto parallax.
        svScroll.getViewTreeObserver()
                .addOnScrollChangedListener(new ViewTreeObserver
                        .OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        parallax(svScroll, imgFoto);
                    }
                });
    }

    // Realiza el efecto parallax sobre la imagen a partir del scroll realizado.
    private void parallax(ScrollView scrollView, ImageView image) {
        int scrollY = scrollView.getScrollY();
        image.setTranslationY(-scrollY / 2);
    }

}
