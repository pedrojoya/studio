package es.iessaladillo.pedrojoya.pr089.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import es.iessaladillo.pedrojoya.pr089.R;

// Adaptador para la lista.
class CancionesAdapter extends ArrayAdapter<Cancion> {

    private final ArrayList<Cancion> mCanciones;
    private final LayoutInflater mInflador;

    // Constructor.
    public CancionesAdapter(Context contexto, ArrayList<Cancion> canciones) {
        super(contexto, R.layout.activity_main_item, canciones);
        mCanciones = canciones;
        mInflador = LayoutInflater.from(contexto);
    }

    // Retorna la vista que se debe "dibujar" para un determinado elemento.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        // Si no se puede reciclar.
        if (convertView == null) {
            // Se obtiene la vista-fila inflando el layout.
            convertView = mInflador.inflate(R.layout.activity_main_item, parent, false);
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
        // Se obtiene la canción que debe mostrar el elemento.
        Cancion cancion = mCanciones.get(position);
        // Se escriben los datos de la canción en las vistas.
        holder.lblNombre.setText(cancion.getNombre());
        holder.lblDuracion.setText(cancion.getDuracion());
        holder.lblAutor.setText(cancion.getAutor());
    }

    // Contenedor de vistas para la vista-fila.
    static class ViewHolder {

        // El contenedor de vistas para un elemento de la lista debe contener...
        private final TextView lblNombre;
        private final TextView lblDuracion;
        private final TextView lblAutor;

        // El constructor recibe la vista-fila.
        public ViewHolder(View itemView) {
            // Se obtienen las vistas de la vista-fila.
            lblNombre = (TextView) itemView
                    .findViewById(R.id.lblNombre);
            lblDuracion = (TextView) itemView
                    .findViewById(R.id.lblDuracion);
            lblAutor = (TextView) itemView
                    .findViewById(R.id.lblAutor);
        }

    }
}
