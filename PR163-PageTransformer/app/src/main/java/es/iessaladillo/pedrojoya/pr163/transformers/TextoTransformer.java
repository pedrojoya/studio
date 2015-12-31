package es.iessaladillo.pedrojoya.pr163.transformers;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr163.R;

public class TextoTransformer implements ViewPager.PageTransformer {

    // Debemos recordar que la traslationX ya se realiza por defecto.
    @Override
    public void transformPage(View view, float position) {
        // Buscamos dentro de la vista el TextView correspondiente al texto.
        TextView lblTexto = (TextView) view.findViewById(R.id.lblTexto);
        // Hacemos que el TextView se traslade horizontalmente en sentido inverso
        // lo que se está trasladando el resto de elementos.
        lblTexto.setTranslationX(-(view.getWidth() * position));
    }

}