package pedrojoya.iessaladillo.es.pr106.base;

import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// M is Model type, VH is ViewHolder type,
@SuppressWarnings("unused")
public abstract class BaseListAdapter<M, VH extends BaseViewHolder> extends RecyclerView
        .Adapter<VH> {

    @NonNull
    private List<M> data = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public void submitList(@NonNull List<M> newData) {
        data = newData;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    @Override
    public final int getItemCount() {
        return data.size();
    }

    public M getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        @SuppressWarnings("SameReturnValue")
        boolean onItemLongClick(View view, int position);
    }

}
