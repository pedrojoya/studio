package es.iessaladillo.pedrojoya.pr109.Adapters;

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
import es.iessaladillo.pedrojoya.pr109.Model.Tarea;
import es.iessaladillo.pedrojoya.pr109.R;

// Adaptador para la lista.
public class TareasAdapter extends ArrayAdapter<Tarea> {

    private final Context context;
    private final List<Tarea> datos;

    public TareasAdapter(Context context, List<Tarea> datos) {
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
        Tarea tarea = datos.get(position);
        contenedor.mLblConcepto.setText(tarea.getConcepto());
        contenedor.mLblResponsable.setText(tarea.getResponsable());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = null;
        try {
            date = df.parse(tarea.getUpdatedAt());
            long time = date.getTime();
            CharSequence hace = DateUtils.getRelativeTimeSpanString(time, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
            contenedor.mLblHace.setText(hace);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.lblConcepto)
        TextView mLblConcepto;
        @InjectView(R.id.lblResponsable)
        TextView mLblResponsable;
        @InjectView(R.id.lblHace)
        TextView mLblHace;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
