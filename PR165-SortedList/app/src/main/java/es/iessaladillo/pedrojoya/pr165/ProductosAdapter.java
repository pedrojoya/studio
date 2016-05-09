package es.iessaladillo.pedrojoya.pr165;

import android.graphics.Paint;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ViewHolder> {

    @SuppressWarnings("UnusedParameters")
    public interface OnItemClickListener {
        void onItemClick(View view, Producto producto, int position);
    }

    @SuppressWarnings("UnusedParameters")
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, Producto producto, int position);
    }

    private final SortedList<Producto> mDatos;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    // Constructor.
    public ProductosAdapter(ArrayList<Producto> datos) {
        // Se crea y configura el SortedList y el objeto listener se será notificado
        // cada vez que se vaya a realizar una operación sobre la lista..
        mDatos = new SortedList<>(Producto.class, new SortedList.Callback<Producto>() {

            // Cuando se deben comparar dos productos para ordenarlos en la lista.
            @Override
            public int compare(Producto item1, Producto item2) {
                // Los items se ordendarán alfabéticamente por su nombre no distinguiendo
                // entre mayúsculas y minúsculas..
                return item1.getNombre().toUpperCase().compareTo(item2.getNombre().toUpperCase());
            }

            // Cuando se inserta un producto en la lista ordenada.
            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            // Cuando se elimina un producto de la lista ordenada.
            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            // Cuando se cambia de posición un producto de la lista ordenada.
            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }

            // Cuando se cambian los datos de un producto de la lista ordenada.
            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            // Cuando se debe comprobar si los contenidos de dos productos son
            // los mismos. Retorna true si son iguales todos los campos.
            @Override
            public boolean areContentsTheSame(Producto oldItem, Producto newItem) {
                return oldItem.getNombre().toUpperCase().equals(newItem.getNombre().toUpperCase()) &&
                        oldItem.getCantidad() == newItem.getCantidad() &&
                        oldItem.getUnidad().toUpperCase().equals(newItem.getUnidad().toUpperCase());
            }

            // Cuando se debe comprobar si dos productos son el mismo.
            // Retorna true si son iguales.
            // Al añadir, si son el mismo producto pero con distintos datos, lo actualiza.
            @Override
            public boolean areItemsTheSame(Producto item1, Producto item2) {
                return item1.getNombre().toUpperCase().equals(item2.getNombre().toUpperCase());
            }

        });
        // Se escriben los datos iniciales en la lista ordenada.
        if (datos != null) {
            mDatos.addAll(datos);
        }
    }

    // Cuando se debe crear una nueva vista para el elemento.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Se infla la especificación XML para obtener la vista.
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_item, parent, false);
        // Se crea el contenedor de vistas para la fila.
        final ViewHolder viewHolder = new ViewHolder(itemView);
        // Se establece el listener para cuando se hace click sobre el elemento.
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    // Se informa al listener.
                    mOnItemClickListener.onItemClick(v,
                            mDatos.get(viewHolder.getAdapterPosition()),
                            viewHolder.getAdapterPosition());
                }
            }
        });
        // Se establece el listener para cuando se hace click largo sobre el elemento.
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLongClickListener != null) {
                    // Se informa al listener.
                    mOnItemLongClickListener.onItemLongClick(v,
                            mDatos.get(viewHolder.getAdapterPosition()),
                            viewHolder.getAdapterPosition());
                    return true;
                } else {
                    return false;
                }
            }
        });
        // Se retorna el contenedor de vistas.
        return viewHolder;
    }

    // Cuando se deben escribir los datos en las subvistas de la
    // vista correspondiente al ítem.
    @Override
    public void onBindViewHolder(ProductosAdapter.ViewHolder holder, int position) {
        // Se escriben los datos del producto en las vistas correspondientes.
        holder.onBind(mDatos.get(position));
    }

    // Retorna el número de ítems gestionados.
    @Override
    public int getItemCount() {
        return mDatos.size();
    }

    // Añade un elemento a la lista ordenada.
    @SuppressWarnings("UnusedReturnValue")
    public int addItem(Producto producto) {
        return mDatos.add(producto);
    }

    // Elimina un elemento de la lista ordenada.
    public boolean removeItem(Producto producto) {
        return mDatos.remove(producto);
    }

    // Conmuta el estado de compra del producto situado en la posición recibida.
    public void toggleComprado(int position) {
        Producto producto = mDatos.get(position);
        producto.toggleComprado();
        notifyItemChanged(position);
    }

    // Retorna un ArrayList con los datos de la lista (útil ya que SortedList
    // no puede almacenarse como extra de un Bundle, por ejemplo en un
    // cambio de orientación).
    public ArrayList<Producto> getData() {
        ArrayList<Producto> productos = new ArrayList<>();
        for (int i = 0; i < mDatos.size(); i++) {
            productos.add(mDatos.get(i));
        }
        return productos;
    }

    // Establece el listener a informar cuando se hace click sobre un
    // elemento de la lista.
    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    // Establece el listener a informar cuando se hace click largo sobre un
    // elemento de la lista.
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    // Contenedor de vistas.
    static class ViewHolder extends RecyclerView.ViewHolder {
        // El contenedor de vistas para un elemento de la lista debe contener...
        private final TextView lblNombre;
        private final TextView lblUnidades;

        // El constructor recibe la vista-fila.
        public ViewHolder(View itemView) {
            super(itemView);
            // Se obtienen las vistas.
            lblNombre = (TextView) itemView.findViewById(R.id.lblNombre);
            lblUnidades = (TextView) itemView.findViewById(R.id.lblUnidades);
        }

        // Cuando se quieren pintar los datos de un producto en las
        // correspondientes vistas.
        public void onBind(Producto producto) {
            lblNombre.setText(producto.getNombre());
            // Si la cantidad en realidad es un valor entero, se escribirá como tal.
            String texto;
            int valor = (int) producto.getCantidad();
            if (valor == producto.getCantidad()) {
                texto = itemView.getContext()
                        .getString(R.string.unidades_data, valor, producto.getUnidad());
            }
            else {
                texto = itemView.getContext()
                        .getString(R.string.unidades_data, producto.getCantidad(),
                                producto.getUnidad());
            }
            lblUnidades.setText(texto);
            // Se establece el estado de tachado de las vistas dependiendo de si
            // el producto está marcado como comprado o no.
            if (producto.isComprado()) {
                lblNombre.setPaintFlags(lblNombre.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                lblUnidades.setPaintFlags(lblUnidades.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            else {
                lblNombre.setPaintFlags(lblNombre.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                lblUnidades.setPaintFlags(lblUnidades.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
    }

}