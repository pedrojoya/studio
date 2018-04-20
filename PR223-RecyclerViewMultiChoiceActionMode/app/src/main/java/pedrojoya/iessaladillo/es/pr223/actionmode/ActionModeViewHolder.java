package pedrojoya.iessaladillo.es.pr223.actionmode;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class ActionModeViewHolder<M> extends RecyclerView.ViewHolder {

    public ActionModeViewHolder(View itemView,
            ActionModeListAdapter<M, ? extends ActionModeViewHolder<M>> adapter) {
        super(itemView);
        itemView.setOnClickListener(v -> {
            if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                if (adapter.getActiveMode() != null) {
                    adapter.onChecked(getAdapterPosition(), !adapter.isChecked(getAdapterPosition()));
                    setChecked(adapter.isChecked(getAdapterPosition()));
                } else {
                    if (adapter.getOnItemClickListener() != null) {
                        adapter.getOnItemClickListener().onItemClick(v,
                                adapter.getItem(getAdapterPosition()), getAdapterPosition(),
                                getItemId());
                    }
                }
            }
        });
        itemView.setOnLongClickListener(v -> {
            if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                adapter.onChecked(getAdapterPosition(), !adapter.isChecked(getAdapterPosition()));
                setChecked(adapter.isChecked(getAdapterPosition()));
                if (adapter.getOnItemLongClickListener() != null) {
                    return adapter.getOnItemLongClickListener().onItemLongClick(v,
                            adapter.getItem(getAdapterPosition()), getAdapterPosition(),
                            getItemId());
                }
            }
            return true;
        });
    }

    @SuppressWarnings("SameParameterValue")
    protected abstract void setChecked(boolean isChecked);

    public abstract void bind(M item);

}
