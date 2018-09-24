package pedrojoya.iessaladillo.es.pr231.base;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import java.util.List;

import androidx.recyclerview.selection.SelectionTracker;

// M for Model, VH for ViewHolder
@SuppressWarnings("unused")
abstract public class BaseAdapter<M, VH extends RecyclerView.ViewHolder> extends ListAdapter<M, VH> {

    private View emptyView;
    protected OnItemClickListener onItemClickListener;
    protected SelectionTracker selectionTracker;

    @SuppressWarnings("SameParameterValue")
    protected BaseAdapter(DiffUtil.ItemCallback<M> diffUtilItemCallback) {
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