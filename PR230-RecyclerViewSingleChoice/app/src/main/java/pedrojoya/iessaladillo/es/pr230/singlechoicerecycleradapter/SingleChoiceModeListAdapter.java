package pedrojoya.iessaladillo.es.pr230.singlechoicerecycleradapter;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
abstract public class SingleChoiceModeListAdapter<M, V extends SingleChoiceModeViewHolder<M>> extends
        ListAdapter<M, V> {

    private final SingleChoiceMode choiceMode;
    private final RecyclerView rv;
    private View emptyView;

    public SingleChoiceModeListAdapter(RecyclerView rv,
            DiffUtil.ItemCallback<M> diffUtilItemCallback) {
        super(diffUtilItemCallback);
        this.rv = rv;
        this.choiceMode = new SingleChoiceMode();
    }

    @Override
    public void submitList(List<M> list) {
        checkEmptyViewVisibility(list == null ? 0 : list.size());
        if (choiceMode.getCheckedPosition() >= 0) {
            onChecked(choiceMode.getCheckedPosition(), false);
        }
        super.submitList(list);
    }

    public void setEmptyView(@NonNull View emptyView) {
        this.emptyView = emptyView;
        checkEmptyViewVisibility(getItemCount());
    }

    private void checkEmptyViewVisibility(int size) {
        if (emptyView != null) {
            emptyView.setVisibility(size == 0 ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @Override
    public M getItem(int position) {
        return super.getItem(position);
    }

    public void onChecked(int position, boolean isChecked) {
        int checked = choiceMode.getCheckedPosition();
        if (checked >= 0) {
            SingleChoiceModeViewHolder viewHolder = (SingleChoiceModeViewHolder) rv
                    .findViewHolderForAdapterPosition(
                    checked);
            if (viewHolder != null) {
                viewHolder.setChecked(false);
            }
        }
        choiceMode.setChecked(position, isChecked);
    }

    public boolean isChecked(int position) {
        return (choiceMode.isChecked(position));
    }

    public int getCheckedPosition() {
        return (choiceMode.getCheckedPosition());
    }

    public void onSaveInstanceState(Bundle state) {
        choiceMode.onSaveInstanceState(state);
    }

    public void onRestoreInstanceState(Bundle state) {
        choiceMode.onRestoreInstanceState(state);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull V holder) {
        super.onViewAttachedToWindow(holder);
        if (holder.getAdapterPosition() != choiceMode.getCheckedPosition()) {
            holder.setChecked(false);
        }
    }

    @CallSuper
    @Override
    public void onBindViewHolder(@NonNull V holder, int position) {
        holder.setChecked(isChecked(position));
        holder.bind(getItem(position));
    }

}