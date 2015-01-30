package es.iessaladillo.pedrojoya.pr111;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

// Adaptador para la lista.
public class AlumnosAdapter extends ArrayAdapter<Alumno> {

    private final Context context;
    private final List<Alumno> datos;

    public AlumnosAdapter(Context context, List<Alumno> datos) {
        super(context, 0, datos);
        this.context = context;
        this.datos = datos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder contenedor;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_main_item, parent, false);
            contenedor = new ViewHolder(convertView);
            convertView.setTag(contenedor);
        } else {
            contenedor = (ViewHolder) convertView.getTag();
        }
        Alumno alumno = datos.get(position);
        contenedor.mLblNombre.setText(alumno.getNombre());
        contenedor.mLblEdad.setText(alumno.getEdad()+"");
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.lblNombre)
        TextView mLblNombre;
        @InjectView(R.id.lblEdad)
        TextView mLblEdad;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
