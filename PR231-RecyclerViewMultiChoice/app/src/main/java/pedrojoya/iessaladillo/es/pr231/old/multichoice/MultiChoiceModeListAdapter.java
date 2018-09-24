package pedrojoya.iessaladillo.es.pr231.old.multichoice;

import android.os.Bundle;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import android.view.View;

import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
abstract public class MultiChoiceModeListAdapter<M, V extends MultiChoiceModeViewHolder<M>> extends
        ListAdapter<M,
        V> {

    private final ManyChoiceMode choiceMode;
    private View emptyView;

    public MultiChoiceModeListAdapter(DiffUtil.ItemCallback<M> diffUtilItemCallback) {
        super(diffUtilItemCallback);
        this.choiceMode = new MultiChoiceMode();
    }

    @Override
    public void submitList(List<M> list) {
        checkEmptyViewVisibility(list == null ? 0 : list.size());
        clearChecks();
        super.submitList(list);
    }

    @Override
    public M getItem(int position) {
        return super.getItem(position);
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

    public void onChecked(int position, boolean isChecked) {
        choiceMode.setChecked(position, isChecked);
    }

    public boolean isChecked(int position) {
        return (choiceMode.isChecked(position));
    }

    public void onSaveInstanceState(Bundle state) {
        choiceMode.onSaveInstanceState(state);
    }

    public void onRestoreInstanceState(Bundle state) {
        choiceMode.onRestoreInstanceState(state);
    }

    public int getCheckedCount() {
        return (choiceMode.getCheckedCount());
    }

    public void clearChecks() {
        choiceMode.clearChecks();
    }

    public void visitChecks(ManyChoiceMode.Visitor v) {
        choiceMode.visitChecks(v);
    }

    @CallSuper
    @Override
    public void onBindViewHolder(@NonNull V holder, int position) {
        holder.setChecked(isChecked(position));
        holder.bind(getItem(position));
    }

}