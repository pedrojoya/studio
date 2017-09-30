package es.iessaladillo.pedrojoya.pr163.transformers;

import android.support.v4.view.ViewPager;
import android.view.View;

public class CubeTransformer implements ViewPager.PageTransformer {

    private static final float ROTACION_MAX = 90.0f;

    // Debemos recordar que la traslationX ya se realiza por defecto.
    @Override
    public void transformPage(View view, float position) {
        // Si la página no está visible no tendrá rotación Y.
        if (position <= -1 || position >= 1) {
            view.setPivotX((float) view.getWidth() / 2);
            view.setRotationY(0.0f);
        } else if (position > 0) {
            // Se girará sobre el eje Y correspondiente al lateral izquierdo.
            view.setPivotX(0);
            view.setRotationY(ROTACION_MAX * position);
        } else {
            // Se trata del panel de la izquierda (-1, 0]
            // Se girará sobre el eje Y correspondiente al lateral derecho.
            view.setPivotX(view.getWidth());
            view.setRotationY(ROTACION_MAX * position);
        }
    }
    
}
