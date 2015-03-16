package es.iessaladillo.pedrojoya.pr012;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AlumnosAdapter extends ArrayAdapter<Alumno> {

    // Variables miembro.
    ArrayList<Alumno> alumnos;
    final LayoutInflater inflador;

    // Constructor.
    public AlumnosAdapter(Context contexto, ArrayList<Alumno> alumnos) {
        super(contexto, R.layout.tarjeta, alumnos);
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
            convertView = inflador.inflate(R.layout.tarjeta, parent, false);
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Se obtiene el alumno que debe mostrar el elemento.
        Alumno alumno = alumnos.get(position);
        // Se escriben los datos del alumno en las vistas.
        holder.imgFoto.setImageResource(alumno.getFoto());
        holder.lblNombre.setText(alumno.getNombre());
        holder.lblCurso.setText(alumno.getCurso() + " " + alumno.getCiclo());
        holder.lblEdad.setText(alumno.getEdad() + " " + getContext().getString(R.string.anios));
        // El fondo del TextView con la edad es diferente si es menor de
        // edad.
        if (alumno.getEdad() < 18) {
            holder.lblEdad.setTextColor(getContext().getResources().getColor(R.color.accent));
        } else {
            holder.lblEdad.setTextColor(getContext().getResources().getColor(R.color.primary_text));
        }
        // Si el alumno es repetidor se muestra el TextView correspondiente.
        if (alumno.isRepetidor()) {
            holder.lblRepetidor.setVisibility(View.VISIBLE);
        } else {
            holder.lblRepetidor.setVisibility(View.INVISIBLE);
        }
    }

    // Contenedor de vistas para la vista-fila.
    public class ViewHolder {

        // El contenedor de vistas para un elemento de la lista debe contener...
        private final ImageView imgFoto;
        private final TextView lblNombre;
        private final TextView lblCurso;
        private final TextView lblEdad;
        private final TextView lblRepetidor;

        // El constructor recibe la vista-fila.
        public ViewHolder(View itemView) {
            // Se obtienen las vistas de la vista-fila.
            imgFoto = (ImageView) itemView
                    .findViewById(R.id.imgFoto);
            lblNombre = (TextView) itemView
                    .findViewById(R.id.lblNombre);
            lblCurso = (TextView) itemView
                    .findViewById(R.id.lblCurso);
            lblEdad = (TextView) itemView
                    .findViewById(R.id.lblEdad);
            lblRepetidor = (TextView) itemView
                    .findViewById(R.id.lblRepetidor);
        }

    }

}

