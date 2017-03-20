package es.iessaladillo.pedrojoya.pr114;

import android.content.Intent;
import android.net.Uri;
import android.provider.CallLog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LlamadasAdapter extends RecyclerView.Adapter<LlamadasAdapter.ViewHolder> {

    private static final String FORMATO_FECHA_HORA = "dd/MM HH:mm";

    private ArrayList<Llamada> mDatos;
    private final SimpleDateFormat mFormateadorFechas;

    public LlamadasAdapter(ArrayList<Llamada> datos) {
        mDatos = datos;
        mFormateadorFechas = new SimpleDateFormat(FORMATO_FECHA_HORA, Locale.getDefault());
    }

    @Override
    public int getItemCount() {
        return mDatos.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_item, parent, false));
    }

    @Override
    public void onBindViewHolder(LlamadasAdapter.ViewHolder holder, int position) {
        holder.bind(mDatos.get(position));
    }

    public void setData(ArrayList<Llamada> data) {
        mDatos = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lblNombre)
        TextView lblNombre;
        @BindView(R.id.imgTipoLlamada)
        ImageView imgTipoLlamada;
        @BindView(R.id.lblNumero)
        TextView lblNumero;
        @BindView(R.id.lblFecha)
        TextView lblFecha;
        @BindView(R.id.lblDuracion)
        TextView lblDuracion;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    marcar(mDatos.get(getAdapterPosition()));
                }
            });
        }

        public void bind(Llamada llamada) {
            lblNombre.setText(TextUtils.isEmpty(llamada.getNombre()) ? lblNombre
                    .getContext().getResources().getString(R.string.desconocido) : llamada
                                      .getNombre());
            lblNumero.setText(llamada.getNumero());
            lblDuracion.setText(formatearDuracion(llamada.getDuracion()));
            lblFecha.setText(mFormateadorFechas.format(new Date(llamada.getFecha())));
            int resIdTipoLlamada;
            int resIdFondoTipoLlamada;
            switch (llamada.getTipo()) {
                case CallLog.Calls.INCOMING_TYPE:
                    resIdTipoLlamada = R.drawable.ic_call_received;
                    resIdFondoTipoLlamada = R.drawable.call_received_background;
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    resIdTipoLlamada = R.drawable.ic_call_missed;
                    resIdFondoTipoLlamada = R.drawable.call_missed_background;
                    break;
                case CallLog.Calls.OUTGOING_TYPE:
                    resIdTipoLlamada = R.drawable.ic_call_made;
                    resIdFondoTipoLlamada = R.drawable.call_made_background;
                    break;
                default:
                    resIdTipoLlamada = R.drawable.ic_call;
                    resIdFondoTipoLlamada = R.drawable.call_background;
                    break;
            }
            imgTipoLlamada.setBackgroundResource(resIdFondoTipoLlamada);
            imgTipoLlamada.setImageResource(resIdTipoLlamada);
        }

        private String formatearDuracion(long duracion) {
            final long segsEnHora = 3600;
            final long minsEnHora = 60;
            long hor = duracion / segsEnHora;
            long min = (duracion - (segsEnHora * hor)) / minsEnHora;
            long seg = duracion - ((hor * segsEnHora) + (min * minsEnHora));
            return (hor > 0 ? hor + " h " : "") + (min > 0 ? min + " m " : "") + seg + " s";
        }

        private void marcar(Llamada llamada) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + llamada.getNumero()));
            lblFecha.getContext().startActivity(intent);
        }

    }

}
