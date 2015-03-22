package es.iessaladillo.pedrojoya.pr048.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import es.iessaladillo.pedrojoya.pr048.R;
import es.iessaladillo.pedrojoya.pr048.modelos.Alumno;

public class AlumnosAdapter extends ArrayAdapter<Alumno> {

    // Variables miembro.
    private final ArrayList<Alumno> alumnos;
    private final LayoutInflater inflador;

    // Constructor.
    public AlumnosAdapter(Context contexto, ArrayList<Alumno> alumnos) {
        super(contexto, R.layout.activity_main_item, alumnos);
        this.alumnos = alumnos;
        // Se obtiene el objeto inflador de layouts.
        inflador = LayoutInflater.from(contexto);
    }

    // Retorna la vista que se debe "dibujar" para un determinado elemento.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        // Si no se puede reciclar.
        if (convertView == null) {
            // Se obtiene la vista-fila inflando el layout.
            convertView = inflador.inflate(R.layout.activity_main_item, parent, false);
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
        // Se obtiene el alumno que debe mostrar el elemento.
        Alumno alumno = alumnos.get(position);
        // Se escriben los datos del alumno en las vistas.
        Picasso.with(getContext())
                .load(alumno.getUrlFoto())
                .into(holder.imgAvatar);
        holder.lblNombre.setText(alumno.getNombre());
        holder.lblDireccion.setText(alumno.getDireccion());
    }

    // Contenedor de vistas para la vista-fila.
    public class ViewHolder {

        // El contenedor de vistas para un elemento de la lista debe contener...
        private final ImageView imgAvatar;
        private final TextView lblNombre;
        private final TextView lblDireccion;

        // El constructor recibe la vista-fila.
        public ViewHolder(View itemView) {
            // Se obtienen las vistas de la vista-fila.
            imgAvatar = (CircleImageView) itemView
                    .findViewById(R.id.imgAvatar);
            lblNombre = (TextView) itemView
                    .findViewById(R.id.lblNombre);
            lblDireccion = (TextView) itemView
                    .findViewById(R.id.lblDireccion);
        }

    }

}

