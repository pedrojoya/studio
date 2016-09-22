package es.iessaladillo.pedrojoya.pr163.transformers;

import android.support.v4.view.ViewPager;
import android.view.View;

public class ProfundidadTransformer implements ViewPager.PageTransformer {

    private static final float ESCALA_MINIMA = 0.25f;

    // Debemos recordar que la traslationX ya se realiza por defecto.
    @Override
    public void transformPage(View view, float position) {
        // Si se trata del panel de la derecha (0, 1)
        if (position > 0 && position < 1) {
            // Su opacidad va desde invisible a completamente visible.
            view.setAlpha(1 - position);
            // Su escala va desde la escala mínima a escala 1.
            view.setScaleX(Math.max(ESCALA_MINIMA, 1 - position));
            view.setScaleY(Math.max(ESCALA_MINIMA, 1 - position));
            // Desplazamos la vista horizontalmente en sentido inverso
            // para que aparezca siempre centrada.
            view.setTranslationX(view.getWidth() * -position);
        }
        // Se realiza el desplazamiento por defecto.
        else {
            view.setAlpha(1);
            view.setTranslationX(0);
            view.setScaleX(1);
            view.setScaleY(1);
        }
    }

}
