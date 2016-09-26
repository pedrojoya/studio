package es.iessaladillo.pedrojoya.pr163.transformers;

import android.support.v4.view.ViewPager;
import android.view.View;

public class UpTransformer implements ViewPager.PageTransformer {

    // Debemos recordar que la traslationX ya se realiza por defecto.
    @Override
    public void transformPage(View view, float position) {
        // Si la página no está visible no tendrá traslación Y.
        if (position <= -1 || position >= 1) {
            view.setTranslationY(0.0f);
        } else {
            // Si se trata del panel de la derecha (0, 1)
            if (position > 0) {
                // Se traslada hacia arriba. El máximo es -(altura * 1)
                view.setTranslationY(-(view.getHeight() * position));
            } else {
                // Se trata del panel de la izquierda o visible entero (-1, 0]
                // Se traslada hacia arriba. El máximo es (-altura * 1)
                view.setTranslationY(-(view.getHeight() * Math.abs(position)));
            }
        }
    }

}
