package pedrojoya.iessaladillo.es.pr243.actionmode;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class ActionModeListAdapter<M, V extends ActionModeViewHolder<M>> extends
        ListAdapter<M, V> implements ActionMode.Callback {

    @SuppressWarnings("unused")
    public interface MultiChoiceModeListener extends ActionMode.Callback {

        void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked);
    }
    private final AppCompatActivity activity;

    private final ManyChoiceMode choiceMode;
    private final MultiChoiceModeListener multichoiceModeListener;
    private ActionMode activeMode;
    private OnItemClickListener<M> onItemClickListener;
    private OnItemLongClickListener<M> onItemLongClickListener;
    private View emptyView;
    public ActionModeListAdapter(AppCompatActivity activity,
            DiffUtil.ItemCallback<M> diffUtilItemCallback,
            MultiChoiceModeListener multiChoiceModeListener) {
        super(diffUtilItemCallback);
        this.activity = activity;
        this.multichoiceModeListener = multiChoiceModeListener;
        choiceMode = new MultiChoiceMode();
    }

    public ActionMode getActiveMode() {
        return activeMode;
    }

    public void toggleChecked(int position) {
        onChecked(position, !isChecked(position));
    }

    public void onChecked(int position, boolean isChecked) {
        choiceMode.setChecked(position, isChecked);
        if (getCheckedCount() == 0 && activeMode != null) {
            activeMode.finish();
        } else {
            if (activeMode == null) {
                activeMode = activity.startSupportActionMode(this);
            }
            multichoiceModeListener.onItemCheckedStateChanged(activeMode, position,
                    getItemId(position), isChecked);
        }
    }

    public int getCheckedCount() {
        return (choiceMode.getCheckedCount());
    }

    public boolean isChecked(int position) {
        return (choiceMode.isChecked(position));
    }

    public void visitChecks(ManyChoiceMode.Visitor v) {
        choiceMode.visitChecks(v);
    }

    @Override
    public M getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        activeMode = mode;
        return multichoiceModeListener.onCreateActionMode(mode, menu);
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return multichoiceModeListener.onPrepareActionMode(mode, menu);
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return multichoiceModeListener.onActionItemClicked(mode, item);
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        if (activeMode != null) {
            visitChecks(position -> {
                onChecked(position, false);
                notifyItemChanged(position);
            });
            activeMode = null;
        }
        multichoiceModeListener.onDestroyActionMode(mode);
    }

    @Override
    public void submitList(List<M> list) {
        checkEmptyViewVisibility(list == null ? 0 : list.size());
        if (activeMode != null) {
            activeMode.finish();
        }
        super.submitList(list);
    }

    public void setOnItemClickListener(OnItemClickListener<M> listener) {
        this.onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<M> listener) {
        this.onItemLongClickListener = listener;
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

    public void onSaveInstanceState(Bundle state) {
        choiceMode.onSaveInstanceState(state);
    }

    public void onRestoreInstanceState(Bundle state) {
        choiceMode.onRestoreInstanceState(state);
        if (getCheckedCount() > 0) {
            activeMode = activity.startSupportActionMode(this);
        }
    }

    @CallSuper
    @Override
    public void onBindViewHolder(@NonNull V holder, int position) {
        holder.setChecked(isChecked(position));
        holder.bind(getItem(position));
    }

    public OnItemClickListener<M> getOnItemClickListener() {
        return onItemClickListener;
    }

    public OnItemLongClickListener<M> getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

}
