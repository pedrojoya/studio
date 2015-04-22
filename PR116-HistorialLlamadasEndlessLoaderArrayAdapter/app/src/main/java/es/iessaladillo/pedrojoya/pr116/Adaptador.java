package es.iessaladillo.pedrojoya.pr116;

import android.content.Context;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

class Adaptador extends ArrayAdapter {

    private static final String FORMATO_FECHA_HORA = "dd/MM HH:mm";
    private static final long SEGS_EN_HORA = 3600;
    private static final long MINS_EN_HORA = 60;

    private final Context mContext;
    private final ArrayList<Llamada> mDatos;
    private final SimpleDateFormat mFormateadorFechas;

    public Adaptador(Context context, ArrayList<Llamada> datos) {
        super(context, 0, datos);
        mContext = context;
        mDatos = datos;
        mFormateadorFechas = new SimpleDateFormat(FORMATO_FECHA_HORA, Locale.getDefault());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = newView(mContext, parent);
        }
        bindView(convertView, mContext, position);
        return convertView;
    }

    // Cuando se va a crear una nueva vista-fila (no es posible reciclar).
    public View newView(Context context, ViewGroup parent) {
        View vista = LayoutInflater.from(context).inflate(R.layout.activity_main_item, parent, false);
        vista.setTag(new ViewHolder(vista));
        return vista;
    }

    // Cuando debe escribirse el registro en la vista-fila.
    public void bindView(View view, Context context, int position) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        Llamada llamada = mDatos.get(position);
        viewHolder.mLblNombre.setText(llamada.getNombre());
        viewHolder.mLblNumero.setText(llamada.getNumero());
        viewHolder.mLblDuracion.setText(formatearDuracion(llamada.getDuracion()));
        viewHolder.mLblFecha.setText(mFormateadorFechas.format(new Date(llamada.getFecha())));
        int resIdTipoLlamada;
        switch (llamada.getTipo()) {
            case CallLog.Calls.INCOMING_TYPE:
                resIdTipoLlamada = android.R.drawable.sym_call_incoming;
                break;
            case CallLog.Calls.MISSED_TYPE:
                resIdTipoLlamada = android.R.drawable.sym_call_missed;
                break;
            case CallLog.Calls.OUTGOING_TYPE:
                resIdTipoLlamada = android.R.drawable.sym_call_outgoing;
                break;
            default:
                resIdTipoLlamada = android.R.drawable.sym_action_call;
                break;
        }
        viewHolder.mImgTipoLlamada.setImageResource(resIdTipoLlamada);
    }

    private String formatearDuracion(long duracion) {
        long hor = duracion / SEGS_EN_HORA;
        long min = (duracion - (SEGS_EN_HORA * hor)) / MINS_EN_HORA;
        long seg = duracion - ((hor * SEGS_EN_HORA) + (min * MINS_EN_HORA));
        return (hor > 0 ? hor + " h " : "") + (min > 0 ? min + " m " : "") + seg + " s";
    }

    static class ViewHolder {
        @InjectView(R.id.lblNombre)
        TextView mLblNombre;
        @InjectView(R.id.imgTipoLlamada)
        ImageView mImgTipoLlamada;
        @InjectView(R.id.lblNumero)
        TextView mLblNumero;
        @InjectView(R.id.lblFecha)
        TextView mLblFecha;
        @InjectView(R.id.lblDuracion)
        TextView mLblDuracion;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
