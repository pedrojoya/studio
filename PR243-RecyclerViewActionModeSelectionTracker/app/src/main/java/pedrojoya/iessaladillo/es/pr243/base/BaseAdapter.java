package pedrojoya.iessaladillo.es.pr243.base;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import androidx.recyclerview.selection.SelectionTracker;

@SuppressWarnings({"WeakerAccess", "unused"})
abstract public class BaseAdapter<M, V extends RecyclerView.ViewHolder> extends ListAdapter<M, V> {

    private View emptyView;
    private OnItemClickListener onItemClickListener;
    protected SelectionTracker selectionTracker;

    public BaseAdapter(DiffUtil.ItemCallback<M> diffUtilItemCallback) {
        super(diffUtilItemCallback);
    }

    @Override
    public void submitList(List<M> list) {
        checkEmptyViewVisibility(list == null ? 0 : list.size());
        super.submitList(list);
    }

    // El adaptador debe recibir el selectionTracker para que el ViewHolder pueda
    // saber el estado de selección del elemento.
    public void setSelectionTracker(SelectionTracker selectionTracker) {
        this.selectionTracker = selectionTracker;
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
    public M getItem(int position) {
        return super.getItem(position);
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

}