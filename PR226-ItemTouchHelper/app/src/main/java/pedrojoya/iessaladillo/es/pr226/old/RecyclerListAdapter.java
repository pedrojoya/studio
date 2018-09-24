package pedrojoya.iessaladillo.es.pr226.old;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import java.util.Collections;
import java.util.List;

// V is ViewModel type, M is Model type.
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class RecyclerListAdapter<M, V extends RecyclerListAdapter.ViewHolder> extends
        RecyclerView.Adapter<V> {

    @SuppressWarnings("unused")
    public interface OnItemClickListener<M> {
        void onItemClick(RecyclerView.Adapter<?> adapter, View view, M item, int position, long id);
    }

    @SuppressWarnings("unused")
    public interface OnItemLongClickListener<M> {
        @SuppressWarnings("SameReturnValue")
        boolean onItemLongClick(RecyclerView.Adapter<?> adapter, View view, M item, int position,
                long id);
    }

    private List<M> data;
    private OnItemClickListener<M> onItemClickListener;
    private OnItemLongClickListener<M> onItemLongClickListener;
    private View emptyView;
    private final RecyclerView.AdapterDataObserver adapterDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            checkEmptyViewVisibility();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            checkEmptyViewVisibility();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            checkEmptyViewVisibility();
        }
    };

    public RecyclerListAdapter(List<M> data) {
        this.data = data;
    }

    public void submitList(List<M> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<M> listener) {
        this.onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<M> listener) {
        this.onItemLongClickListener = listener;
    }


    public void setEmptyView(@NonNull View emptyView) {
        if (this.emptyView != null) {
            unregisterAdapterDataObserver(adapterDataObserver);
        }
        this.emptyView = emptyView;
        registerAdapterDataObserver(adapterDataObserver);
        checkEmptyViewVisibility();
    }

    private void checkEmptyViewVisibility() {
        if (emptyView != null) {
            emptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @Override
    public final int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public M getItem(int position) {
        return data.get(position);
    }

    public void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(M item) {
        data.add(item);
        notifyItemInserted(data.size() - 1);
    }

    public void insertItem(M item, int position) {
        data.add(position, item);
        notifyItemInserted(position);
    }

    public void swapItems(int from, int to) {
        Collections.swap(data, from, to);
        notifyItemMoved(from, to);
    }

    public abstract class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            if (onItemClickListener != null) {
                itemView.setOnClickListener(
                        v -> onItemClickListener.onItemClick(RecyclerListAdapter.this, v,
                                getItem(getAdapterPosition()), getAdapterPosition(), getItemId()));
            }
            if (onItemLongClickListener != null) {
                itemView.setOnLongClickListener(
                        v -> onItemLongClickListener.onItemLongClick(RecyclerListAdapter.this, v,
                                getItem(getAdapterPosition()), getAdapterPosition(), getItemId()));
            }
        }

    }

}
