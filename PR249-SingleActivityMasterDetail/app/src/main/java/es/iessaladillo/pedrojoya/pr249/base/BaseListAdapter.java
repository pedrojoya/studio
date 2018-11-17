package es.iessaladillo.pedrojoya.pr249.base;

import android.view.View;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

// M for Model, VH for ViewHolder
abstract public class BaseListAdapter<M, VH extends BaseViewHolder> extends
        ListAdapter<M, VH> {

    protected OnItemClickListener onItemClickListener;

    protected BaseListAdapter(DiffUtil.ItemCallback<M> diffUtilItemCallback) {
        super(diffUtilItemCallback);
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