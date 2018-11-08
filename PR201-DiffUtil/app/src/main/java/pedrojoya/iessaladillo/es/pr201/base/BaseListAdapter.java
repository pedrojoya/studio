package pedrojoya.iessaladillo.es.pr201.base;

import android.view.View;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

// V is ViewModel type, M is Model type.
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class BaseListAdapter<M, V extends BaseViewHolder> extends RecyclerView.Adapter<V> {

    protected OnItemClickListener onItemClickListener;
    protected OnItemLongClickListener onItemLongClickListener;
    @NonNull
    private List<M> data;

    public BaseListAdapter(@NonNull List<M> data) {
        this.data = data;
    }

    public void submitList(@NonNull List<M> newData) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(
            new BaseDiffUtilCallback(data, newData), true);
        data.clear();
        getList().addAll(newData);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    public List<M> getList() {
        return data;
    }

    @Override
    public final int getItemCount() {
        return data.size();
    }

    public M getItem(int position) {
        return data.get(position);
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    protected abstract boolean areContentsTheSame(M oldItem, M newItem);

    protected abstract boolean areItemsTheSame(M oldItem, M newItem);

    public interface OnItemClickListener {

        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {

        boolean onItemLongClick(View view, int position);
    }

    private class BaseDiffUtilCallback extends DiffUtil.Callback {

        private final List<M> oldData;

        private final List<M> newData;

        BaseDiffUtilCallback(List<M> oldData, List<M> newData) {
            this.oldData = oldData;
            this.newData = newData;
        }

        @Override
        public int getOldListSize() {
            return oldData == null ? 0 : oldData.size();
        }

        @Override
        public int getNewListSize() {
            return newData == null ? 0 : newData.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return BaseListAdapter.this.areItemsTheSame(oldData.get(oldItemPosition),
                newData.get(newItemPosition));
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return BaseListAdapter.this.areContentsTheSame(oldData.get(oldItemPosition),
                newData.get(newItemPosition));
        }

    }

}
