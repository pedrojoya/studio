package es.iessaladillo.pedrojoya.pr171;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import es.iessaladillo.pedrojoya.pr171.db.Alumno;
import es.iessaladillo.pedrojoya.pr171.db.Asignatura_Alumno;

// Adaptador para la lista.
class AlumnosAdapter extends ArrayAdapter<Alumno> {

    private final ArrayList<Alumno> mAlumnos;
    private final LayoutInflater mInflador;

    // Constructor.
    public AlumnosAdapter(Context contexto, ArrayList<Alumno> alumnos) {
        super(contexto, R.layout.activity_main_item, alumnos);
        mAlumnos = alumnos;
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
        // Se obtiene el alumno que debe mostrar el elemento.
        Alumno alumno = mAlumnos.get(position);
        // Se escriben los datos del alumno en las vistas.
        holder.lblNombre.setText(alumno.getNombre());
        if (alumno.getCurso() != null) {
            holder.lblCurso.setText(alumno.getCurso().getNombre());
        }
        holder.lblDireccion.setText(alumno.getDireccion());
        // Se obtienen las asignaturas del alumno.
        List<Asignatura_Alumno> asignaturas = alumno.getAsignaturas();
        String texto;
        if (asignaturas != null && !asignaturas.isEmpty()) {
            texto = "";
            for (Asignatura_Alumno asignatura_alumno : asignaturas) {
                texto = texto + (TextUtils.isEmpty(texto)?"":" | ") + asignatura_alumno.getAsignatura().getNombre();
            }
        }
        else {
            texto = getContext().getString(R.string.sin_asignaturas);
        }
        holder.lblAsignaturas.setText(texto);
        Glide.with(getContext()).load(
                alumno.getAvatar())
                .into(holder.imgAvatar);
    }

    // Contenedor de vistas para la vista-fila.
    static class ViewHolder {

        // El contenedor de vistas para un elemento de la lista debe contener...
        private final CircleImageView imgAvatar;
        private final TextView lblNombre;
        private final TextView lblCurso;
        private final TextView lblDireccion;
        private final TextView lblAsignaturas;

        // El constructor recibe la vista-fila.
        public ViewHolder(View itemView) {
            // Se obtienen las vistas de la vista-fila.
            imgAvatar = (CircleImageView) ViewCompat.requireViewById(itemView, R.id.imgAvatar);
            lblNombre = (TextView) itemView
                    .findViewById(R.id.lblNombre);
            lblCurso = (TextView) itemView
                    .findViewById(R.id.lblCurso);
            lblDireccion = (TextView) itemView
                    .findViewById(R.id.lblDireccion);
            lblAsignaturas = (TextView) itemView
                    .findViewById(R.id.lblAsignaturas);
        }

    }
}
