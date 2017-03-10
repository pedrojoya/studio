package es.iessaladillo.pedrojoya.pr123.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import java.util.ArrayList;

// Adaptador para spinner en toolbar. Para que se muestre siguiendo el tema
// adecuado debe implementar ThemedSpinnerAdapter y recibir el contexto de
// la toolbar.
public class ToolbarSpinnerAdapter extends ArrayAdapter<String> implements ThemedSpinnerAdapter {

    private final Helper mDropDownHelper;
    private final ArrayList<String> mDatos;

    public ToolbarSpinnerAdapter(Context context, ArrayList<String> datos) {
        super(context, android.R.layout.simple_spinner_dropdown_item, datos);
        // Se crea un ayudante para aplicar el tema adecuado.
        mDropDownHelper = new Helper(context);
        mDatos = datos;
    }

    // Cuando se debe pintar un elemento en la lista desplegable del spinner.
    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            // Se obtiene el inflador proporcionado pro el ayudante (para que
            // se use el tema adecuado).
            LayoutInflater inflater = mDropDownHelper.getDropDownViewInflater();
            convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }
        // Se escribe el dato (android.R.layout.simple_spinner_dropdown_item
        // sólo posee un CheckedTextView).
        ((CheckedTextView) convertView).setText(mDatos.get(position));
        // Se retorna la vista configurada.
        return convertView;
    }

    @Override
    public void setDropDownViewTheme(Resources.Theme theme) {
        // Se establece el tema de la lista desplegable del spinner
        // en el ayudante.
        mDropDownHelper.setDropDownViewTheme(theme);
    }

    @Override
    public Resources.Theme getDropDownViewTheme() {
        // Se obtiene el tema de la lista desplegable del spinner desde el
        // ayudante.
        return mDropDownHelper.getDropDownViewTheme();
    }

}