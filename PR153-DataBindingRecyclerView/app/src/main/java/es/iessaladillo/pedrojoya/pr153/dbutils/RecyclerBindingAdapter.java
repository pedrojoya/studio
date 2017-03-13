package es.iessaladillo.pedrojoya.pr153.dbutils;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class RecyclerBindingAdapter<T extends RecyclerBindingAdapter.ViewModel> extends
        RecyclerView.Adapter<RecyclerBindingAdapter.ViewHolder> {

    public interface ViewModel {
        @SuppressWarnings("SameReturnValue")
        int getLayoutId();
    }

    // Interfaz que debe implementar el listener para cuando se haga click sobre un elemento.
    @SuppressWarnings("UnusedParameters")
    public interface OnItemClickListener<T extends RecyclerBindingAdapter.ViewModel> {
        void onItemClick(View view, T model, int position);
    }

    // Interfaz que debe implementar el listener para cuando se haga click largo sobre un elemento.
    @SuppressWarnings("UnusedParameters")
    public interface OnItemLongClickListener<T extends RecyclerBindingAdapter.ViewModel> {
        void onItemLongClick(View view, T model, int position);
    }

    private final List<T> mData;
    private final int mModelBRId;
    private OnItemLongClickListener<T> onItemLongClickListener;
    private OnItemClickListener<T> onItemClickListener;

    @SuppressWarnings("SameParameterValue")
    public RecyclerBindingAdapter(List<T> data, int modelBRId) {
        this.mData = data;
        this.mModelBRId = modelBRId;
    }

    @Override
    public int getItemViewType(int position) {
        // Se retorna el id del layout correspondiente a ese elemento.
        return mData.get(position).getLayoutId();
    }

    @Override
    public RecyclerBindingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Se infla la especificación XML para obtener la vista-fila.
        View itemView = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        // Se crea el contenedor de vistas para la fila.
        final ViewHolder viewHolder = new ViewHolder(itemView);

        // Se establecen los listener de la vista correspondiente al ítem
        // y de las subvistas.

        // Cuando se hace click sobre el elemento.
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    // Se informa al listener.
                    onItemClickListener.onItemClick(v, mData.get(viewHolder.getAdapterPosition()),
                            viewHolder.getAdapterPosition());
                }
            }
        });
        // Cuando se hace click largo sobre el elemento.
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    // Se informa al listener.
                    onItemLongClickListener.onItemLongClick(v,
                            mData.get(viewHolder.getAdapterPosition()),
                            viewHolder.getAdapterPosition());
                    return true;
                } else {
                    return false;
                }
            }
        });
        // Se retorna el contenedor.
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerBindingAdapter.ViewHolder holder, int position) {
        final T model = mData.get(position);
        holder.getBinding().setVariable(mModelBRId, model);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    // Elimina un elemento de la lista.
    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    // Añade un elemento a la lista.
    public void addItem(T model) {
        // Se añade el elemento.
        mData.add(model);
        // Se notifica que se ha insertado un elemento en la última posición.
        notifyItemInserted(mData.size() - 1);
    }

    // Intercambia dos elementos de la lista.
    void swapItems(int from, int to) {
        // Se realiza el intercambio.
        Collections.swap(mData, from, to);
        // Se notifica el movimiento.
        notifyItemMoved(from, to);
    }


    // Establece el listener a informar cuando se hace click sobre un elemento de la lista.
    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.onItemClickListener = listener;
    }

    // Establece el listener a informar cuando se hace click largo sobre un elemento de la lista.
    public void setOnItemLongClickListener(OnItemLongClickListener<T> listener) {
        this.onItemLongClickListener = listener;
    }

    // Contenedor de vistas para la vista-fila.
    static class ViewHolder extends RecyclerView.ViewHolder {

        // Data binding.
        private final ViewDataBinding binding;

        // El constructor recibe la vista-fila.
        public ViewHolder(View itemView) {
            super(itemView);
            // Se obtienen el data binding para dicha vista-fila.
            binding = DataBindingUtil.bind(itemView);
        }

        // Retorna el binding correspondiente al ítem.
        public ViewDataBinding getBinding() {
            return binding;
        }

    }

}
