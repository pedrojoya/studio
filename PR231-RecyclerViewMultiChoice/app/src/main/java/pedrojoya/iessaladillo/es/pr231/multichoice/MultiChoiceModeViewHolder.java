package pedrojoya.iessaladillo.es.pr231.multichoice;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class MultiChoiceModeViewHolder<M> extends RecyclerView.ViewHolder {

    public MultiChoiceModeViewHolder(View itemView,
            MultiChoiceModeListAdapter<M, ? extends MultiChoiceModeViewHolder<M>> adapter) {
        super(itemView);
        itemView.setOnClickListener(v -> {
            if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                adapter.onChecked(getAdapterPosition(), !adapter.isChecked(getAdapterPosition()));
                setChecked(adapter.isChecked(getAdapterPosition()));
            }
        });
    }

    @SuppressWarnings("SameParameterValue")
    protected abstract void setChecked(boolean isChecked);

    public abstract void bind(M item);

}
