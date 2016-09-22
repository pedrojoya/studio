package es.iessaladillo.pedrojoya.pr163.transformers;

import android.support.v4.view.ViewPager;
import android.view.View;

public class PuertasTransformer implements ViewPager.PageTransformer {

    private static final float GIRO_MAX_GRADOS = 30.0f;

    // Debemos recordar que la traslationX ya se realiza por defecto.
    @Override
    public void transformPage(View view, float position) {
        // Si la página no está visible no tendrá rotación sobre el eje Y.
        if (position <= -1 || position >= 1) {
            view.setRotationY(0.0f);
        } else {
            // Se rota sobre el eje Y hasta 30 grados.
            view.setRotationY(position * -GIRO_MAX_GRADOS);
        }
    }

}
