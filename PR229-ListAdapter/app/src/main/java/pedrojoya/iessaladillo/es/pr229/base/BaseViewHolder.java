package pedrojoya.iessaladillo.es.pr229.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    protected BaseViewHolder(View itemView, BaseListAdapter adapter) {
        super(itemView);
        if (adapter.getOnItemClickListener() != null) {
            itemView.setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    adapter.getOnItemClickListener().onItemClick(v, getAdapterPosition());
                }
            });
        }
        if (adapter.getOnItemLongClickListener() != null) {
            itemView.setOnLongClickListener(v -> {
                //noinspection SimplifiableIfStatement
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    return adapter.getOnItemLongClickListener().onItemLongClick(v, getAdapterPosition());
                }
                return false;
            });
        }
    }

}
