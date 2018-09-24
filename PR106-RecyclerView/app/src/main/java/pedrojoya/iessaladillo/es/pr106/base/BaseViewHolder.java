package pedrojoya.iessaladillo.es.pr106.base;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

// M is Model.
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

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
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    return onItemLongClickListener.onItemLongClick(v, getAdapterPosition());
                }
                return false;
            });
        }
    }

}
