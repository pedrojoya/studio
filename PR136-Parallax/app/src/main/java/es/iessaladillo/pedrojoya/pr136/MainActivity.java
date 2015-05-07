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
        parallax(svScroll, imgFoto);
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
        int baseColor = getResources().getColor(R.color.colorPrimary);
        int imageHeight = getResources().getDimensionPixelSize(R.dimen.image_height);
        float alpha = Math.min(1, (float) scrollY / imageHeight);
        toolbar.setBackgroundColor(getColorWithAlpha(alpha, baseColor));
    }

    public int getColorWithAlpha(float alpha, int baseColor) {
        int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
        int rgb = 0x00ffffff & baseColor;
        return a + rgb;
    }

}
