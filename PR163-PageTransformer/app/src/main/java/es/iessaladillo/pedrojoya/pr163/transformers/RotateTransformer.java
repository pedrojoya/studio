package es.iessaladillo.pedrojoya.pr163.transformers;

import android.support.v4.view.ViewPager;
import android.view.View;

public class RotateTransformer implements ViewPager.PageTransformer {

    private static final float GIRO_MAX_GRADOS = 30.0f;

    // Debemos recordar que la traslationX ya se realiza por defecto.
    @Override
    public void transformPage(View view, float position) {
        // Si la página no está visible no tendrá rotación sobre el eje Z
        // y tendrá opacidad máxima.
        if (position <= -1 || position >= 1) {
            view.setRotation(0.0f);
            view.setAlpha(1);
        } else {
            // Si se trata del panel de la derecha (0, 1)
            if (position > 0) {
                // Se rota hasta -30 grados sobre el eje Z.
                view.setRotation(-(GIRO_MAX_GRADOS * position));
                view.setAlpha(1 - position);
            } else {
                // Se trata del panel de la izquierda o visible entero (-1, 0]
                // Se rota hasta -30 grados sobre el eje Z
                view.setRotation(+(GIRO_MAX_GRADOS * Math.abs(position)));
                view.setAlpha(1 - Math.abs(position));
            }
        }
    }

}
