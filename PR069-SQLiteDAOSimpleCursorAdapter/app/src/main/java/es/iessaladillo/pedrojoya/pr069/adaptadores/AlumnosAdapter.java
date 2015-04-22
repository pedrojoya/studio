package es.iessaladillo.pedrojoya.pr069.adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.Random;

import es.iessaladillo.pedrojoya.pr069.R;
import es.iessaladillo.pedrojoya.pr069.bd.Instituto;

public class AlumnosAdapter extends SimpleCursorAdapter {

    private final int mLayout;
    private final Random mAleatorio;
    private final TextDrawable.IBuilder mDrawableBuilder;

    public AlumnosAdapter(Context context, int layout, Cursor c,
                     String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        mLayout = layout;
        mAleatorio = new Random();
        mDrawableBuilder = TextDrawable.builder()
                .beginConfig()
                .width(100)
                .height(100)
                .toUpperCase()
                .endConfig()
                .round();
    }

    // Cuando debe escribirse el registro en la vista-fila.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String nombre = cursor.getString(
                cursor.getColumnIndexOrThrow(Instituto.Alumno.NOMBRE));
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.lblNombre.setText(nombre);
        holder.lblCurso.setText(cursor.getString(
                cursor.getColumnIndexOrThrow(Instituto.Alumno.CURSO)));
        holder.lblDireccion.setText(cursor.getString(
                cursor.getColumnIndexOrThrow(Instituto.Alumno.DIRECCION)));
        holder.imgAvatar.setImageDrawable(mDrawableBuilder.build(nombre.substring(0, 1),
                ColorGenerator.MATERIAL.getRandomColor()));
    }

    // Cuando se va a crear una nueva vista-fila (no es posible reciclar).
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View vista = LayoutInflater.from(context).inflate(mLayout, parent, false);
        vista.setTag(new ViewHolder(vista));
        return vista;
    }

    // Contenedor de vistas para la vista-fila.
    static class ViewHolder {

        // El contenedor de vistas para un elemento de la lista debe contener...
        private final ImageView imgAvatar;
        private final TextView lblNombre;
        private final TextView lblCurso;
        private final TextView lblDireccion;

        // El constructor recibe la vista-fila.
        public ViewHolder(View itemView) {
            // Se obtienen las vistas de la vista-fila.
            imgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
            lblNombre = (TextView) itemView
                    .findViewById(R.id.lblNombre);
            lblCurso = (TextView) itemView
                    .findViewById(R.id.lblCurso);
            lblDireccion = (TextView) itemView
                    .findViewById(R.id.lblDireccion);
        }

    }

}