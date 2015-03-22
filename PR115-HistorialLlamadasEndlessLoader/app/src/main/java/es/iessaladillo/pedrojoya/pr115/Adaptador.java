package es.iessaladillo.pedrojoya.pr115;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

class Adaptador extends SimpleCursorAdapter {

    private static final String FORMATO_FECHA_HORA = "dd/MM HH:mm";
    private static final long SEGS_EN_HORA = 3600;
    private static final long MINS_EN_HORA = 60;

    private final int mLayout;
    private final SimpleDateFormat mFormateadorFechas;

    public Adaptador(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        mLayout = layout;
        mFormateadorFechas = new SimpleDateFormat(FORMATO_FECHA_HORA, Locale.getDefault());
    }

    // Cuando debe escribirse el registro en la vista-fila.
    @Override
    public void bindView(@NonNull View view, Context context, @NonNull Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        String sNombre = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.CACHED_NAME));
        if (TextUtils.isEmpty(sNombre)) {
            sNombre = context.getResources().getString(R.string.desconocido);
        }
        viewHolder.mLblNombre.setText(sNombre);
        viewHolder.mLblNumero.setText(cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.NUMBER)));
        long lDuracion = cursor.getLong(cursor.getColumnIndexOrThrow(CallLog.Calls.DURATION));
        viewHolder.mLblDuracion.setText(formatearDuracion(lDuracion));
        Date fecha = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(CallLog.Calls.DATE)));
        viewHolder.mLblFecha.setText(mFormateadorFechas.format(fecha));
        int resIdTipoLlamada;
        switch (cursor.getInt(cursor.getColumnIndexOrThrow(CallLog.Calls.TYPE))) {
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

    // Cuando se va a crear una nueva vista-fila (no es posible reciclar).
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View vista = LayoutInflater.from(context).inflate(mLayout, parent, false);
        vista.setTag(new ViewHolder(vista));
        return vista;
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
