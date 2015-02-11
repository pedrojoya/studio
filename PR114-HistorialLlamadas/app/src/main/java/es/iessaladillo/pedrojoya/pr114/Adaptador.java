package es.iessaladillo.pedrojoya.pr114;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Adaptador extends SimpleCursorAdapter {
    private final int mLayout;
    private final SimpleDateFormat mFormateadorFechas;

    public Adaptador(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        mLayout = layout;
        mFormateadorFechas = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        String sNombre = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.CACHED_NAME));
        if (TextUtils.isEmpty(sNombre)) {
            sNombre = "Desconocido";
        }
        viewHolder.mLblNombre.setText(sNombre);
        viewHolder.mLblNumero.setText(cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.NUMBER)));
        long lDuracion = cursor.getLong(cursor.getColumnIndexOrThrow(CallLog.Calls.DURATION));
        viewHolder.mLblDuracion.setText(formatearDuracion(lDuracion));
        Date fecha = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(CallLog.Calls.DATE)));
        viewHolder.mLblFecha.setText(mFormateadorFechas.format(fecha));
        String sTipoLlamada;
        switch (cursor.getInt(cursor.getColumnIndexOrThrow(CallLog.Calls.TYPE))) {
            case CallLog.Calls.INCOMING_TYPE:
                sTipoLlamada = "(entrante)";
                break;
            case CallLog.Calls.MISSED_TYPE:
                sTipoLlamada = "(perdida)";
                break;
            case CallLog.Calls.OUTGOING_TYPE:
                sTipoLlamada = "(saliente)";
                break;
            default:
                sTipoLlamada = "";
                break;
        }
        viewHolder.mLblTipoLlamada.setText(sTipoLlamada);
    }

    private String formatearDuracion(long duracion) {
        long hor = duracion / 3600;
        long min = (duracion -(3600 * hor)) / 60;
        long seg = duracion - ((hor * 3600) + (min * 60));
        return (hor > 0?hor + " h ":"") + (min > 0?min + " m ":"") + seg + " s";
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View vista = LayoutInflater.from(context).inflate(R.layout.activity_main_item, parent, false);
        vista.setTag(new ViewHolder(vista));
        return vista;
    }

    static class ViewHolder {
        @InjectView(R.id.lblNombre)
        TextView mLblNombre;
        @InjectView(R.id.lblTipoLlamada)
        TextView mLblTipoLlamada;
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
