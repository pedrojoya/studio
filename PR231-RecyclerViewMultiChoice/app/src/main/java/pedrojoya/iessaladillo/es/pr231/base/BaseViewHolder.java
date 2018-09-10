package pedrojoya.iessaladillo.es.pr231.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    protected BaseViewHolder(@NonNull View itemView, BaseAdapter adapter) {
        super(itemView);
        itemView.setOnClickListener(v -> {
            if (adapter.getOnItemClickListener() != null) {
                adapter.getOnItemClickListener().onItemClick(v, getAdapterPosition());
            }
        });
    }
}
