package pedrojoya.iessaladillo.es.pr201.base;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

public abstract class BaseViewHolder<M> extends RecyclerView.ViewHolder {

    protected BaseViewHolder(View itemView, BaseListAdapter.OnItemClickListener onItemClickListener,
            BaseListAdapter.OnItemLongClickListener onItemLongClickListener) {
        super(itemView);
        if (onItemClickListener != null) {
            itemView.setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(v, getAdapterPosition());
                }
            });
        }
        if (onItemLongClickListener != null) {
            itemView.setOnLongClickListener(v -> {
                //noinspection SimplifiableIfStatement
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    return onItemLongClickListener.onItemLongClick(v, getAdapterPosition());
                }
                return false;
            });
        }
    }

}
