package pedrojoya.iessaladillo.es.pr201.base;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    protected BaseViewHolder(@NonNull View itemView,
        BaseListAdapter.OnItemClickListener onItemClickListener,
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
