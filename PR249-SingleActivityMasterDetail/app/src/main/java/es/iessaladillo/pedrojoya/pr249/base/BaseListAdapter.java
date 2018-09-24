package es.iessaladillo.pedrojoya.pr249.base;

import android.view.View;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

// M for Model, VH for ViewHolder, K for selection tracker Key.
abstract public class BaseListAdapter<M, VH extends BaseViewHolder, K> extends
        ListAdapter<M, VH> {

    private View emptyView;
    protected OnItemClickListener onItemClickListener;
    @SuppressWarnings({"WeakerAccess", "unused"})
    protected SelectionTracker<K> selectionTracker;

    @SuppressWarnings("WeakerAccess")
    public BaseListAdapter(DiffUtil.ItemCallback<M> diffUtilItemCallback) {
        super(diffUtilItemCallback);
    }

    @Override
    public void submitList(List<M> list) {
        checkEmptyViewVisibility(list == null ? 0 : list.size());
        super.submitList(list);
    }

    // El adaptador debe recibir el selectionTracker para que el ViewHolder pueda
    // saber el estado de selección del elemento.
    @SuppressWarnings("unused")
    public void setSelectionTracker(SelectionTracker<K> selectionTracker) {
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

    @SuppressWarnings("unused")
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