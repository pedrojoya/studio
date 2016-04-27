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
        ViewHolder holder;
        // Si no se puede reciclar.
        if (convertView == null) {
            // Se obtiene la vista-fila inflando el layout.
            convertView = mInflador.inflate(R.layout.fragment_lista_item, parent, false);
            // Se crea el contenedor de vistas para la vista-fila.
            holder = new ViewHolder(convertView);
            // Se almacena el contenedor en la vista.
            convertView.setTag(holder);
        }
        // Si se puede reciclar.
        else {
            // Se obtiene el contenedor de vistas desde la vista reciclada.
            holder = (ViewHolder) convertView.getTag();
        }
        // Se escriben los datos en las vistas del contenedor de vistas.
        onBindViewHolder(holder, position);
        // Se retorna la vista que representa el elemento.
        return convertView;
    }

    // Cuando se deben escribir los datos en la vista del elemento.
    private void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mDatos.get(position));
    }

    // Contenedor de vistas para la vista-fila.
    static class ViewHolder {

        // El contenedor de vistas para un elemento de la lista debe contener...
        private final TextView lblNombre;
        private final TextView lblAutor;
        private final TextView lblAnio;

        // El constructor recibe la vista-fila.
        public ViewHolder(View itemView) {
            // Se obtienen las vistas de la vista-fila.
            lblNombre = (TextView) itemView
                    .findViewById(R.id.lblNombre);
            lblAutor = (TextView) itemView
                    .findViewById(R.id.lblAutor);
            lblAnio = (TextView) itemView
                    .findViewById(R.id.lblAnio);
        }

        // Escribe los datos de la obra en las vistas.
        public void bind(Obra obra) {
            lblNombre.setText(obra.getNombre());
            lblAutor.setText(obra.getAutor());
            lblAnio.setText(String.valueOf(obra.getAnio()));
        }

    }

}