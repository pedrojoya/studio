package es.iessaladillo.pedrojoya.pr028.adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import es.iessaladillo.pedrojoya.pr028.R;
import es.iessaladillo.pedrojoya.pr028.bd.Instituto;

public class AlumnosAdapter extends SimpleCursorAdapter {

    private final int mLayout;

    public AlumnosAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        mLayout = layout;
    }

    // Cuando debe escribirse el registro en la vista-fila.
    @Override
    public void bindView(@NonNull View view, Context context, @NonNull Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.lblNombre.setText(
                cursor.getString(cursor.getColumnIndexOrThrow(Instituto.Alumno.NOMBRE)));
        viewHolder.lblCurso.setText(
                cursor.getString(cursor.getColumnIndexOrThrow(Instituto.Alumno.CURSO)));
        viewHolder.lblDireccion.setText(
                cursor.getString(cursor.getColumnIndexOrThrow(Instituto.Alumno.DIRECCION)));
        Glide.with(context).load(
                cursor.getString(cursor.getColumnIndexOrThrow(Instituto.Alumno.AVATAR)))
                .into(viewHolder.imgAvatar);
    }

    // Cuando se va a crear una nueva vista-fila (no es posible reciclar).
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View vista = LayoutInflater.from(context).inflate(mLayout, parent, false);
        vista.setTag(new ViewHolder(vista));
        return vista;
    }

    static class ViewHolder {
        CircleImageView imgAvatar;
        TextView lblNombre;
        TextView lblCurso;
        TextView lblDireccion;

        ViewHolder(View view){
            imgAvatar = (CircleImageView) view.findViewById(R.id.imgAvatar);
            lblNombre = (TextView) view.findViewById(R.id.lblNombre);
            lblCurso = (TextView) view.findViewById(R.id.lblCurso);
            lblDireccion = (TextView) view.findViewById(R.id.lblDireccion);
        }
    }
}
