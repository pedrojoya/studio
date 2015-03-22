package es.iessaladillo.pedrojoya.pr049.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr049.R;
import es.iessaladillo.pedrojoya.pr049.modelos.Obra;

public class ObrasAdapter extends ArrayAdapter<Obra> {

    // Variables.
    private final ArrayList<Obra> mDatos;
    private final LayoutInflater mInflador;

    // Clase contenedor de vistas.
    private class ContenedorVistas {
        TextView lblNombre;
        TextView lblAutor;
        TextView lblAnio;
    }

    // Constructor.
    public ObrasAdapter(Context contexto, ArrayList<Obra> datos) {
        super(contexto, 0, datos);
        this.mDatos = datos;
        // Se obtiene un objeto inflador de layouts.
        mInflador = LayoutInflater.from(contexto);
    }

    // Cuando se debe pintar un elemento de la lista.
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ContenedorVistas contenedor;
        // Si no se puede reciclar.
        if (convertView == null) {
            // Se infla el layout para obtener la vista.
            convertView = mInflador.inflate(R.layout.fragment_lista_item,
                    parent, false);
            // Se obtienen las vistas y se almacenan en el contenedor.
            contenedor = new ContenedorVistas();
            contenedor.lblNombre = (TextView) convertView
                    .findViewById(R.id.lblNombre);
            contenedor.lblAutor = (TextView) convertView
                    .findViewById(R.id.lblAutor);
            contenedor.lblAnio = (TextView) convertView
                    .findViewById(R.id.lblAnio);
            // Se guarda el contenedor en el tag de la vista.
            convertView.setTag(contenedor);
        } else {
            // Si se puede reciclar se obtiene el contenedor.
            contenedor = (ContenedorVistas) convertView.getTag();
        }
        // Se escriben los datos en las vistas.
        bindView(contenedor, position);
        // Se retorna la vista.
        return convertView;
    }

    // Escribe los datos en las vistas del contenedor recibido.
    private void bindView(ContenedorVistas contenedor, int position) {
        Obra obra = mDatos.get(position);
        contenedor.lblNombre.setText(obra.getNombre());
        contenedor.lblAutor.setText(obra.getAutor());
        contenedor.lblAnio.setText(obra.getAnio() + "");
    }

}