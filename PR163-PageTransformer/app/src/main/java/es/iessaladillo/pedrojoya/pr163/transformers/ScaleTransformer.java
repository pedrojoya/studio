package es.iessaladillo.pedrojoya.pr163.transformers;

import android.support.v4.view.ViewPager;
import android.view.View;

public class ScaleTransformer implements ViewPager.PageTransformer {

    private static final float ESCALA_MINIMA = 0.50f;

    // Debemos recordar que la traslationX ya se realiza por defecto.
    @Override
    public void transformPage(View view, float position) {
        // Si la página no está visible se resetean los valores por defecto.
        if (position <= -1 || position >= 1) {
            view.setScaleX(1);
            view.setScaleY(1);
        } else {
            // Si se trata del panel de la derecha (0, 1)
            if (position > 0) {
                // Se escala como hasta como máximo la mitad de su tamaño.
                view.setScaleX(Math.max(ESCALA_MINIMA, 1 - position));
                view.setScaleY(Math.max(ESCALA_MINIMA, 1 - position));
            }
            // Si se trata del panel de la izquierda o visible entero (-1, 0]
            else {
                // Se escala como hasta como máximo la mitad de su tamaño.
                view.setScaleX(Math.max(ESCALA_MINIMA, 1 + position));
                view.setScaleY(Math.max(ESCALA_MINIMA, 1 + position));
            }
        }
    }

}
