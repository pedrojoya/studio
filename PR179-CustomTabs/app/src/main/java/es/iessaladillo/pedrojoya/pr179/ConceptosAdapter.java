package es.iessaladillo.pedrojoya.pr179;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ConceptosAdapter extends RecyclerView.Adapter<ConceptosAdapter.ViewHolder> {

    // Interfaz que debe implementar el listener para cuando se haga click
    // sobre un elemento.
    @SuppressWarnings("UnusedParameters")
    public interface OnItemClickListener {
        void onItemClick(View view, Concepto concepto, int position);
    }

    private OnItemClickListener onItemClickListener;
    private final ArrayList<Concepto> mConceptos;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Se infla la especificación de layout del elemento.
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_item, parent, false);
        // Se crea el viewholder para el elemento.
        final ViewHolder viewHolder = new ViewHolder(itemView);
        // Cuando se hace click sobre el elemento.
        itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                // Se informa al listener.
                onItemClickListener.onItemClick(v, mConceptos.get(viewHolder.getAdapterPosition()),
                        viewHolder.getAdapterPosition());
            }
        });
        // Se retorna el contenedor.
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Se obtiene el concepto correspondiente.
        Concepto concepto = mConceptos.get(position);
        // Se escriben sus datos en las vistas.
        holder.bind(concepto);
    }

    @Override
    public int getItemCount() {
        return mConceptos.size();
    }

    public ConceptosAdapter(ArrayList<Concepto> conceptos) {
        mConceptos = conceptos;
    }


    // Establece el listener a informar cuando se hace click sobre un ítem.
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    // Clase ViewHolder para el elemento.
    static class ViewHolder extends RecyclerView.ViewHolder {

        // El contenedor de vistas para un elemento del grid debe contener...
        private final ImageView imgFoto;
        private final TextView lblEnglish;
        private final TextView lblSpanish;

        public ViewHolder(View itemView) {
            super(itemView);
            // Se obtienen las vistas del elemento.
            imgFoto = (ImageView) itemView.findViewById(R.id.imgFoto);
            lblEnglish = (TextView) itemView.findViewById(R.id.lblEnglish);
            lblSpanish = (TextView) itemView.findViewById(R.id.lblSpanish);
        }

        // Escribe los datos del concepto en las vistas.
        public void bind(Concepto concepto) {
            imgFoto.setImageResource(concepto.getFotoResId());
            lblEnglish.setText(concepto.getEnglish());
            lblSpanish.setText(concepto.getSpanish());
        }

    }

}
