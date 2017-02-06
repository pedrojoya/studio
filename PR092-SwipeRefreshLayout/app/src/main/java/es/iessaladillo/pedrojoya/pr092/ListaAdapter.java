package es.iessaladillo.pedrojoya.pr092;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ViewHolder> {

    private final ArrayList<String> mDatos;

    // Constructor.
    public ListaAdapter(ArrayList<String> datos) {
        mDatos = datos;
    }

    // Cuando se debe crear una nueva vista para el elemento.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Se infla la especificación XML para obtener la vista.
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        // Se crea el contenedor de vistas para la fila y se retorna.
        return new ViewHolder(itemView);
    }

    // Cuando se deben escribir los datos en las subvistas de la
    // vista correspondiente al ítem.
    @Override
    public void onBindViewHolder(ListaAdapter.ViewHolder holder, int position) {
        // Se escriben los datos del producto en las vistas correspondientes.
        holder.onBind(mDatos.get(position));
    }

    // Retorna el número de ítems gestionados.
    @Override
    public int getItemCount() {
        return mDatos.size();
    }

    // Añade un elemento a la lista ordenada.
    public void addItem(String elemento) {
        mDatos.add(elemento);
        notifyItemInserted(mDatos.size() - 1);
    }

    // Contenedor de vistas.
    static class ViewHolder extends RecyclerView.ViewHolder {
        // El contenedor de vistas para un elemento de la lista debe contener...
        private final TextView text1;

        // El constructor recibe la vista-fila.
        public ViewHolder(View itemView) {
            super(itemView);
            // Se obtienen las vistas.
            text1 = (TextView) itemView.findViewById(android.R.id.text1);
        }

        // Cuando se quieren pintar los datos de un elemento en las
        // correspondientes vistas.
        public void onBind(String elemento) {
            text1.setText(elemento);
        }
    }

}
