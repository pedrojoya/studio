package es.iessaladillo.pedrojoya.pr086;

import java.util.List;

import android.content.Context;
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

class AlumnosAdapter extends ArrayAdapter<Alumno> {

    // Variables.
    private final Context contexto;
    private final List<Alumno> alumnos;

    // Clase interna contenedora de vistas.
    private class Contenedor {
        final TextView lblNombre;
        final TextView lblTelefono;
        final TextView lblDireccion;
        final TextView lblCurso;
        final ImageView imgPopupMenu;

        public Contenedor(TextView lblNombre, TextView lblTelefono,
                TextView lblDireccion, TextView lblCurso, ImageView imgPopupMenu) {
            this.lblNombre = lblNombre;
            this.lblTelefono = lblTelefono;
            this.lblDireccion = lblDireccion;
            this.lblCurso = lblCurso;
            this.imgPopupMenu = imgPopupMenu;
        }

    }

    // Constructor.
    public AlumnosAdapter(Context contexto, List<Alumno> alumnos) {
        // Se llama al constructor del padre.
        super(contexto, R.layout.activity_main_item, alumnos);
        // Se hace copia local de los parámetros.
        this.contexto = contexto;
        this.alumnos = alumnos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contenedor contenedor;
        // Si no podemos reciclar.
        if (convertView == null) {
            // Se infla el layout.
            convertView = LayoutInflater.from(contexto).inflate(
                    R.layout.activity_main_item, parent, false);
            // Se obtienen las vistas.
            TextView lblNombre = (TextView) convertView
                    .findViewById(R.id.lblNombre);
            TextView lblDireccion = (TextView) convertView
                    .findViewById(R.id.lblDireccion);
            TextView lblTelefono = (TextView) convertView
                    .findViewById(R.id.lblTelefono);
            TextView lblCurso = (TextView) convertView
                    .findViewById(R.id.lblCurso);
            ImageView imgPopupMenu = (ImageView) convertView
                    .findViewById(R.id.imgPopupMenu);
            // Se crea un nuevo objeto listener para cuando se pulse en la
            // imagen, a cuyo construtor se le pasa el alumno del que se trata.
            imgPopupMenu.setOnClickListener(new imgPopupMenuOnClickListener(
                    alumnos.get(position)));
            // Se crea un contenedor de vistas.
            contenedor = new Contenedor(lblNombre, lblTelefono, lblDireccion,
                    lblCurso, imgPopupMenu);
            // Se almacena el contenedor en la propiedad tag de la vista.
            convertView.setTag(contenedor);
        } else {
            // Se obtiene el contenedor de la propiedad tag de la vista.
            contenedor = (Contenedor) convertView.getTag();
        }
        // Se escriben los datos en las vistas.
        Alumno alumno = alumnos.get(position);
        contenedor.lblNombre.setText(alumno.getNombre());
        contenedor.lblTelefono.setText(alumno.getTelefono());
        contenedor.lblDireccion.setText(alumno.getDireccion());
        contenedor.lblCurso.setText(alumno.getCurso());
        // Se retorna la vista que debe mostrarse.
        return convertView;
    }

    // Clase listener para pulsación sobre el icono del PopupMenu.
    private class imgPopupMenuOnClickListener implements OnClickListener {

        // Alumno asociado.
        final Alumno alumno;

        // Constructor. Recibe el alumno asociado.
        public imgPopupMenuOnClickListener(Alumno alumno) {
            this.alumno = alumno;
        }

        // Cuando se hace click sobre el icono.
        @Override
        public void onClick(View v) {
            // Se crea el menú.
            PopupMenu popup = new PopupMenu(contexto, v);
            // Se infla la especificación de menú.
            MenuInflater inflador = popup.getMenuInflater();
            inflador.inflate(R.menu.activity_main_item_popup, popup.getMenu());
            // En API 14 popup.inflate(R.menu.actions)
            // Se crea el listener para cuando se pulse un ítem del menú, a cuyo
            // constructor se le pasa el alumno asociado.
            popup.setOnMenuItemClickListener(new PopupMenuOnMenuItemClickListener(
                    alumno));
            // Se muestra el menú.
            popup.show();
        }
    }

    private class PopupMenuOnMenuItemClickListener implements
            OnMenuItemClickListener {

        // Alumno asociado.
        final Alumno alumno;

        // Constructor. Recibe el alumno asociado.
        public PopupMenuOnMenuItemClickListener(Alumno alumno) {
            this.alumno = alumno;
        }

        // Cuando se selecciona un ítem del PopupMenu.
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
            case R.id.mnuLlamar:
                Toast.makeText(contexto, "Llamar a " + alumno.getNombre(),
                        Toast.LENGTH_LONG).show();
                break;
            case R.id.mnuEnviarMensaje:
                Toast.makeText(contexto,
                        "Enviar mensaje a " + alumno.getNombre(),
                        Toast.LENGTH_LONG).show();
                break;
            }
            return true;
        }

    }

}
