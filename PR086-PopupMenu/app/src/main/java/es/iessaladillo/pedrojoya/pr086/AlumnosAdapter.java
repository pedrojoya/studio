package es.iessaladillo.pedrojoya.pr086;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

class AlumnosAdapter extends ArrayAdapter<Alumno> {

    private final List<Alumno> mAlumnos;
    private final LayoutInflater mInflador;

    public AlumnosAdapter(Context contexto, List<Alumno> alumnos) {
        super(contexto, R.layout.activity_main_item, alumnos);
        mAlumnos = alumnos;
        mInflador = LayoutInflater.from(contexto);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        // Si no se puede reciclar.
        if (convertView == null) {
            // Se obtiene la vista-fila inflando el layout.
            convertView = mInflador.inflate(R.layout.activity_main_item, parent, false);
            // Se crea el contenedor de vistas para la vista-fila.
            holder = new ViewHolder(convertView);
            // Se almacena el contenedor en la vista.
            convertView.setTag(holder);
        } else {
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
        Alumno alumno = mAlumnos.get(position);
        holder.lblNombre.setText(alumno.getNombre());
        holder.lblTelefono.setText(alumno.getTelefono());
        holder.lblDireccion.setText(alumno.getDireccion());
        holder.lblCurso.setText(alumno.getCurso());
        // Se crea un nuevo objeto listener para cuando se pulse en la
        // imagen, a cuyo construtor se le pasa el mAlumno del que se trata.
        holder.imgPopupMenu.setOnClickListener(
                new ImgPopupMenuOnClickListener(mAlumnos.get(position)));

    }

    // Contenedor de vistas para la vista-fila.
    static class ViewHolder {

        // El contenedor de vistas para un elemento de la lista debe contener...
        private final TextView lblNombre;
        private final TextView lblTelefono;
        private final TextView lblDireccion;
        private final TextView lblCurso;
        private final ImageView imgPopupMenu;

        // El constructor recibe la vista-fila.
        public ViewHolder(View itemView) {
            // Se obtienen las vistas de la vista-fila.
            lblNombre = (TextView) itemView.findViewById(R.id.lblNombre);
            lblTelefono = (TextView) itemView.findViewById(R.id.lblTelefono);
            lblDireccion = (TextView) itemView.findViewById(R.id.lblDireccion);
            lblCurso = (TextView) itemView.findViewById(R.id.lblCurso);
            imgPopupMenu = (ImageView) itemView.findViewById(R.id.imgPopupMenu);
        }

    }

    // Clase listener para pulsación sobre el icono del PopupMenu.
    private class ImgPopupMenuOnClickListener implements OnClickListener {

        private final Alumno mAlumno;

        // Constructor. Recibe el alumno asociado.
        public ImgPopupMenuOnClickListener(Alumno alumno) {
            mAlumno = alumno;
        }

        // Cuando se hace click sobre el icono.
        @Override
        public void onClick(View v) {
            // Se crea el menú.
            PopupMenu popup = new PopupMenu(getContext(), v);
            // Se infla la especificación de menú.
            MenuInflater inflador = popup.getMenuInflater();
            inflador.inflate(R.menu.activity_main_item_popup, popup.getMenu());
            // Se crea el listener para cuando se pulse un ítem del menú, a cuyo
            // constructor se le pasa el mAlumno asociado.
            popup.setOnMenuItemClickListener(new PopupMenuOnMenuItemClickListener(mAlumno));
            // Se muestra el menú.
            popup.show();
        }
    }

    private class PopupMenuOnMenuItemClickListener implements OnMenuItemClickListener {

        // Alumno asociado.
        final Alumno alumno;

        // Constructor. Recibe el mAlumno asociado.
        public PopupMenuOnMenuItemClickListener(Alumno alumno) {
            this.alumno = alumno;
        }

        // Cuando se selecciona un ítem del PopupMenu.
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.mnuLlamar:
                    Toast.makeText(getContext(),
                            getContext().getString(R.string.llamar_a, alumno.getNombre()),
                            Toast.LENGTH_LONG).show();
                    break;
                case R.id.mnuEnviarMensaje:
                    Toast.makeText(getContext(),
                            getContext().getString(R.string.enviar_mensaje_a, alumno.getNombre()),
                            Toast.LENGTH_LONG).show();
                    break;
            }
            return true;
        }

    }

}
