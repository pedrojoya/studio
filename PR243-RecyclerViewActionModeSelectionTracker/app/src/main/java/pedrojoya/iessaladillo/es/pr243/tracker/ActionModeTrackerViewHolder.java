package pedrojoya.iessaladillo.es.pr243.tracker;

import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class ActionModeTrackerViewHolder<M extends Parcelable> extends RecyclerView.ViewHolder {

    public ActionModeTrackerViewHolder(View itemView,
            ActionModeTrackerListAdapter<M, ? extends ActionModeTrackerViewHolder<M>> adapter) {
        super(itemView);
        itemView.setOnClickListener(v -> {
            if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                if (adapter.getActionMode() != null) {
//                    adapter.onChecked(getAdapterPosition(), !adapter.isChecked
//                            (getAdapterPosition()));
//                    setChecked(adapter.isChecked(getAdapterPosition()));
                    setChecked(true);
                } else {
                    if (adapter.getOnItemClickListener() != null) {
                        adapter.getOnItemClickListener().onItemClick(v,
                                adapter.getItem(getAdapterPosition()), getAdapterPosition(),
                                getItemId());
                    }
                }
            }
        });
//        itemView.setOnLongClickListener(v -> {
//            if (getAdapterPosition() != RecyclerView.NO_POSITION) {
//                adapter.onChecked(getAdapterPosition(), !adapter.isChecked(getAdapterPosition()));
//                setChecked(adapter.isChecked(getAdapterPosition()));
//                if (adapter.getOnItemLongClickListener() != null) {
//                    return adapter.getOnItemLongClickListener().onItemLongClick(v,
//                            adapter.getItem(getAdapterPosition()), getAdapterPosition(),
//                            getItemId());
//                }
//            }
//            return true;
//        });
    }

    @SuppressWarnings("SameParameterValue")
    protected abstract void setChecked(boolean isChecked);

    public abstract void bind(M item);

}
