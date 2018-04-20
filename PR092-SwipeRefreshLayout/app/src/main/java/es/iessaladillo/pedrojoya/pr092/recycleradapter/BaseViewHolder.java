package es.iessaladillo.pedrojoya.pr092.recycleradapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseViewHolder<M> extends RecyclerView.ViewHolder {

    @SuppressWarnings("WeakerAccess")
    public BaseViewHolder(View itemView, BaseListAdapter<M, ? extends BaseViewHolder<M>> adapter) {
        super(itemView);
        if (adapter.getOnItemClickListener() != null) {
            itemView.setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    adapter.getOnItemClickListener().onItemClick(v,
                            adapter.getItem(getAdapterPosition()), getAdapterPosition(),
                            getItemId());
                }
            });
        }
        if (adapter.getOnItemLongClickListener() != null) {
            itemView.setOnLongClickListener(v -> {
                //noinspection SimplifiableIfStatement
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    return adapter.getOnItemLongClickListener().onItemLongClick(v,
                            adapter.getItem(getAdapterPosition()), getAdapterPosition(),
                            getItemId());
                }
                return false;
            });
        }
    }

    public abstract void bind(M item);

}
