package es.iessaladillo.pedrojoya.pr132.base;

import android.view.View;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

// M for Model, VH for ViewHolder, K for selection tracker Key.
abstract public class BaseListAdapter<M, VH extends BaseViewHolder> extends ListAdapter<M, VH> {

    private View emptyView;
    protected OnItemClickListener onItemClickListener;

    @SuppressWarnings("SameParameterValue")
    protected BaseListAdapter(DiffUtil.ItemCallback<M> diffUtilItemCallback) {
        super(diffUtilItemCallback);
    }

    @Override
    public void submitList(List<M> list) {
        checkEmptyViewVisibility(list == null ? 0 : list.size());
        super.submitList(list);
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