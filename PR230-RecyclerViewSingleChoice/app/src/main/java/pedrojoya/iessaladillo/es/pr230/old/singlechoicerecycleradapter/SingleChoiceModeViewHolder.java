package pedrojoya.iessaladillo.es.pr230.old.singlechoicerecycleradapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class SingleChoiceModeViewHolder<M> extends RecyclerView.ViewHolder {

    protected SingleChoiceModeViewHolder(View itemView,
            SingleChoiceModeListAdapter<M, ? extends SingleChoiceModeViewHolder<M>> adapter) {
        super(itemView);
        itemView.setOnClickListener(v -> {
            if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                adapter.onChecked(getAdapterPosition(), true);
                setChecked(true);
            }
        });
    }

    @SuppressWarnings("SameParameterValue")
    protected abstract void setChecked(boolean isChecked);

    public abstract void bind(M item);

}
