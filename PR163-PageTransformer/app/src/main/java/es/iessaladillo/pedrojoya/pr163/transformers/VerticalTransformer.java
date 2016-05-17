package es.iessaladillo.pedrojoya.pr163.transformers;

import android.support.v4.view.ViewPager;
import android.view.View;

public class VerticalTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View view, float position) {
        // Si la página no está visible, se hace transparente.
        if (position <= -1 || position >= 1) {
            view.setTranslationX(0);
            view.setTranslationY(0);
        } else {
            // Se contraresta la transición por defecto para que se quede en la
            // misma posición.
            view.setTranslationX(view.getWidth() * -position);
            // Se traslada verticalmente, tanto como se haya desplazado.
            view.setTranslationY(view.getHeight() * position);
        }
    }

}