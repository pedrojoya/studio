package pedrojoya.iessaladillo.es.pr229.old;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import java.util.List;

// V is ViewModel type, M is Model type.
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class RecyclerListAdapter<M, V extends RecyclerListAdapter.ViewHolder> extends
        ListAdapter<M, V> {

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

    private OnItemClickListener<M> onItemClickListener;
    private OnItemLongClickListener<M> onItemLongClickListener;
    private View emptyView;

    public RecyclerListAdapter(DiffUtil.ItemCallback<M> diffUtilItemCallback) {
        super(diffUtilItemCallback);
        checkEmptyViewVisibility(getItemCount());
    }

    public void setOnItemClickListener(OnItemClickListener<M> listener) {
        this.onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<M> listener) {
        this.onItemLongClickListener = listener;
    }

    public void setEmptyView(@NonNull View emptyView) {
        this.emptyView = emptyView;
        checkEmptyViewVisibility(getItemCount());
    }

    private void checkEmptyViewVisibility(int size) {
        if (emptyView != null) {
            emptyView.setVisibility(size == 0 ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @Override
    public void submitList(List<M> list) {
        checkEmptyViewVisibility(list == null ? 0 : list.size());
        super.submitList(list);
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
